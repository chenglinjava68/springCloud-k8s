package com.weilus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.feign.clients")
@ComponentScan({"com.weilus","com.feign.fallbacks"})
@Controller
public class ResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceApplication.class, args);
	}

	@Autowired
	TestProperties properties;

	@RequestMapping("/me")
//	@PreAuthorize("#oauth2.hasScope('oauth-test')")
	public @ResponseBody Object getUser(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@RequestMapping("/client")
	@PreAuthorize("#oauth2.hasScope('resource')")
	public @ResponseBody Object client(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@GetMapping("test")
	public @ResponseBody Object test(){
		return Collections.singletonMap(properties.getName(),properties.getAge());
	}
}
