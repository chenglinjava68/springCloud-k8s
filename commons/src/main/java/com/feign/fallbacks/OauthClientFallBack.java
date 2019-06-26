package com.feign.fallbacks;

import com.feign.clients.OauthClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * Created by liutq on 2018/12/12.
 */
@Component
public class OauthClientFallBack implements OauthClient {
    @Override
    public Map checkToken(String token) {
        return Collections.singletonMap("error","oauth认证中心不可用!");
    }
}
