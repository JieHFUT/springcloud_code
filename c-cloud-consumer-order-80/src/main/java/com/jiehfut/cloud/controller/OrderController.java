package com.jiehfut.cloud.controller;

import com.jiehfut.cloud.entities.PayDTO;
import com.jiehfut.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * post   - pay/add      - Pay
 * delete - pay/del{id}  -
 * put    - pay/update   - PayTDO
 * get    - pay/get/{id} -
 * get    - pay/get      -
 */

@RestController
public class OrderController {

    // 在引入服务注册中心后，使用服务治理功能，实现微服务之间的动态注册与发现
    // public static final String PaymentSrv_URL = "http://localhost:8001"; //先写死，硬编码

    // 实现 consul 的微服务注册于发现时候 - 实现动态注册，解决硬编码
    // consul 天生支持负载均衡，需要配置一个注解 @LoadBalanced 在 RestTemplate 上
    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    /**
     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是 get 请求
     * 我们底层调用的是 post方法，模拟消费者发送 get 请求，客户端消费者
     * 参数可以不添加 @RequestBody
     * @param payDTO
     * @return
     */
    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add", payDTO, ResultData.class);
    }

    /**
     * 根据主键 ID 来删除某一条流水记录
     * @param id
     * @return
     */
    @GetMapping("/consumer/pay/del/{id}")
    public ResultData deletePay(Integer id){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/del/" + id, id, ResultData.class);
    }

    /**
     * 更新某一条流水记录
     * @param payDTO
     * @return
     */
    @GetMapping("/consumer/pay/update")
    public ResultData updatePay(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/update", payDTO, ResultData.class);
    }

    /**
     * 按照主键 ID 来获取一条流水记录
     * @param id
     * @return
     */
    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/"+id, ResultData.class, id);
    }

    /**
     * 获取全部流水记录列表
     * @return
     */
    @GetMapping("/consumer/pay/get")
    public ResultData getPayInfoList() {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get", ResultData.class);
    }











    /**
     * 8001 & 8002 等微服务已经注册进入（注册中心已经完成分布式配置并且完成数据持久化）注册中心
     * 消费者（客户端）发出的请求需要经过客户端负载均衡后自己选择向哪一个微服务请求
     * @return
     */
    @GetMapping("/consumer/pay/get/info")
    public String getInfoByConsul() {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
    }


    /**
     * 如何做到在客户端能够获取已经注册的服务提供者的清单呢？
     * 使用一个组件：DiscoveryClient
     */
    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery() {
        List<String> services = discoveryClient.getServices(); // 获取所有的服务微服务清单
        for (String element : services) {
            System.out.println(element);
        }
        System.out.println("===================================");
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service"); // 获取这个名称的服务微服务清单
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }
        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }
    /**
         cloud-consumer-order
         cloud-payment-service
         consul
         ===================================
         cloud-payment-service	jieHFUT	8001	http://jieHFUT:8001
         cloud-payment-service	jieHFUT	8002	http://jieHFUT:8002
     */

    /**
     * 默认轮询算法进行负载均衡，还有一种是随机选择
     *
     * 负载均衡算法：rest 接口第几次请求数 % 服务器集群总的数量 = 实际调用服务器位置下标，每次服务重新启动后 rest 接口计数从 1开始
     * List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service")
     * instances[0] = 127.0.0.1:8002
     * instances[1] = 127.0.0.1:8001
     * 8001 & 8002 称为一个集群，这个集群有 2台机器，集群总数是 2，按照轮询请求
     * RoundRobinLoadBalancer：轮询集群方式
     * RandomLoadBalancer：随机找一个微服务进行请求
     *
     * 这两个接口都实现了 ReactorServiceInstanceLoadBalancer 空接口
     * public interface ReactorServiceInstanceLoadBalancer extends ReactorLoadBalancer<ServiceInstance> { }
     * public interface ReactorLoadBalancer<T> extends ReactiveLoadBalancer<T> {}
     *
     * The ReactiveLoadBalancer implementation that is used by default is RoundRobinLoadBalancer.
     * To switch to a different implementation, either for selected services or all of them, you can use the custom LoadBalancer configurations mechanism.
     * For example, the following configuration can be passed via @LoadBalancerClient annotation to switch to using the RandomLoadBalancer:
     *
     * public class CustomLoadBalancerConfiguration {
     * 	  @Bean
     *    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
     * 		LoadBalancerClientFactory loadBalancerClientFactory) {
     * 		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
     * 		return new RandomLoadBalancer(loadBalancerClientFactory
     * 				.getLazyProvider(name, ServiceInstanceListSupplier.class),
     * 				name);
     *    }
     * }
     *
     */



}

