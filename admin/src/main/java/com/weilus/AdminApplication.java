package com.weilus;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import spring.boot.admin.turbine.config.TurbineAutoConfiguration;

/**
 * @author 1
 * @program springCloud
 * @date 2019-05-10 10:30
 **/
@SpringBootApplication(exclude = TurbineAutoConfiguration.class)
@EnableDiscoveryClient
@EnableTurbine
@EnableAdminServer
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
