package com.weilus.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liutaiq
 * @program springCloud
 * @date 2019-05-28 14:44
 **/
@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.feign.clients")
@SpringBootApplication(scanBasePackages = {"com.weilus.gateway","com.feign.fallbacks"})
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }
}
