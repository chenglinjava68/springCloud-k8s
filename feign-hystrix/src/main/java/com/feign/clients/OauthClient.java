package com.feign.clients;

import com.feign.fallbacks.OauthClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="oauth",fallback = OauthClientFallBack.class)
public interface OauthClient {

    @GetMapping(value="oauth/check_token")
    Map checkToken(@RequestParam("token") String token);

}
