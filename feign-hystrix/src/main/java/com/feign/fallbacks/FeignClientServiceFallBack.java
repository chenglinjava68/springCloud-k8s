package com.feign.fallbacks;

import com.feign.clients.FeignClientService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Created by liutq on 2018/12/12.
 */
@Component
public class FeignClientServiceFallBack implements FeignClientService {
        @Override
        public String sayHello(@RequestBody Map<String, String> map) {
            return "feign-call: Hello "+map.get("name");
        }

        @Override
        public String hiMan(@RequestBody Map<String, String> map) {
            return "feign-call: Hi "+map.get("name");
        }
}
