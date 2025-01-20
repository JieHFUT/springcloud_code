package com.jiehfut.cloud.controller;



import com.jiehfut.cloud.apis.PayFeignApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 测试断路器的控制层 - 兜底（即为降级）
 * Resilience4j CircuitBreaker 的例子
 * 在 PayFeignApi 接口类中已经有下面接口
 */
@RestController
public class OrderCircuitController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping(value = "/feign/pay/circuit/{id}") // 已经在断路器的 feign 接口声明 && 8001（PayCircuitController）
    // 声明（断路器）要调用哪个微服务（在 yml 中配置的启动熔断器的微服务），如果产生了服务异常，给一个兜底的服务降级的方法（服务）
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitFallback")
    public String myCircuitBreaker(@PathVariable("id") Integer id) {
        // 该微服务正常调用走接口调用服务
        return payFeignApi.myCircuit(id);
    }
    // 异常或者服务降级情况兜底：myCircuitFallback 就是服务降级后的兜底处理方法
    public String myCircuitFallback(Integer id, Throwable t) {
        // 这里是容错处理逻辑，返回备用结果
        return "myCircuitFallback，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
    }

    /**
     * 断路器（熔断）的默认配置类
     public class CircuitBreakerConfig implements Serializable {
         private static final long serialVersionUID = -5429814941777001669L;
         public static final int DEFAULT_FAILURE_RATE_THRESHOLD = 50; // Percentage
         public static final int DEFAULT_SLOW_CALL_RATE_THRESHOLD = 100; // Percentage
         public static final int DEFAULT_WAIT_DURATION_IN_OPEN_STATE = 60; // Seconds
         public static final int DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN_STATE = 10;
         public static final int DEFAULT_MINIMUM_NUMBER_OF_CALLS = 100;
         public static final int DEFAULT_SLIDING_WINDOW_SIZE = 100;
         public static final int DEFAULT_SLOW_CALL_DURATION_THRESHOLD = 60; // Seconds
         public static final int DEFAULT_WAIT_DURATION_IN_HALF_OPEN_STATE = 0; // Seconds. It is an optional parameter
         public static final SlidingWindowType DEFAULT_SLIDING_WINDOW_TYPE = SlidingWindowType.COUNT_BASED;
         public static final boolean DEFAULT_WRITABLE_STACK_TRACE_ENABLED = true;
         private static final Predicate<Throwable> DEFAULT_RECORD_EXCEPTION_PREDICATE = throwable -> true;
         private static final Predicate<Throwable> DEFAULT_IGNORE_EXCEPTION_PREDICATE = throwable -> false;
         private static final Function<Clock, Long> DEFAULT_TIMESTAMP_FUNCTION = clock -> System.nanoTime();
         private static final TimeUnit DEFAULT_TIMESTAMP_UNIT = TimeUnit.NANOSECONDS;
         private static final Predicate<Object> DEFAULT_RECORD_RESULT_PREDICATE = (Object object) -> false;

         ......
     }
     */

    /**
     * resilience4j bulkhead 的例子（隔离机制-信号量仓壁）
     * 1. 8001-PayCircuitController.myBulkhead
     * 2. commons-apis-PayFeignApi
     * 3. 80-pom-(resilience4j-bulkhead)
     * 4. 80-yml
     * 5. 80-controller
     * @param id
     * @return
     */
    @GetMapping(value = "/feign/pay/bulkhead/{id}")
    //                 该隔离使用在哪个微服务上（.yml）             调不通方法，直接兜底出结果       指定使用信号量
    @Bulkhead(name = "cloud-payment-service",fallbackMethod = "myBulkheadFallback",type = Bulkhead.Type.SEMAPHORE)
    public String myBulkhead(@PathVariable("id") Integer id) {
        return payFeignApi.myBulkhead(id);
    }
    public String myBulkheadFallback(Throwable t) {
        return "myBulkheadFallback，隔板超出最大数量限制，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
    }


    /**
     * 隔离机制，通过设置线程池 && 排队队列，实现隔离
     * 与信号量隔离请求路径一样即可，这里做演示，设置不一样
     * @param id
     * @return 与信号量返回不一样 CompletableFuture<String>
     */
    @GetMapping(value = "/feign/pay/bulkhead-threadpool/{id}")
    //                 该隔离使用在哪个微服务上（.yml）             调不通方法，直接兜底出结果         指定使用线程池
    @Bulkhead(name = "cloud-payment-service",fallbackMethod = "myBulkheadPoolFallback",type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> myBulkheadThreadPool(@PathVariable("id") Integer id) {
        System.out.println("线程名称：" + Thread.currentThread().getName() + "......开始进入");
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("线程名称：" + Thread.currentThread().getName() + "......准备离开");

        return CompletableFuture.supplyAsync(() -> payFeignApi.myBulkhead(id) + "\t" + "Bulkhead.Type.THREADPOOL");
    }
    public CompletableFuture<String> myBulkheadPoolFallback(Throwable t, Integer id) {
        return CompletableFuture.supplyAsync(() -> "Bulkhead.Type.THREADPOOL，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~");
    }


    /**
     * 限流机制：限制服务器接收请求的次数
     * 1. 8001-PayCircuitController.myRatelimit
     * 2. commons-apis-PayFeignApi
     * 3. 80-pom-(resilience4j-ratelimiter)
     * 4. 80-yml
     * 5. 80-controller
     * @param id
     * @return
     */
    @GetMapping(value = "/feign/pay/ratelimit/{id}")
    @RateLimiter(name = "cloud-payment-service",fallbackMethod = "myRatelimitFallback")
    public String myRatelimit(@PathVariable("id") Integer id) {
        return payFeignApi.myRatelimit(id);
    }
    public String myRatelimitFallback(Integer id,Throwable t) {
        return "你被限流了，禁止访问/(ㄒoㄒ)/~~";
    }







}