package com.feign.clients;

import com.feign.fallbacks.FeignClientServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name="feign-service",
        fallback = FeignClientServiceFallBack.class)
public interface FeignClientService {

    @RequestMapping(value="/api/sayHello",method=RequestMethod.POST)
    String sayHello(@RequestBody Map<String, String> map);

    @RequestMapping(value="/api/hiMan",method=RequestMethod.POST)
    String hiMan(@RequestBody Map<String, String> map);
}
