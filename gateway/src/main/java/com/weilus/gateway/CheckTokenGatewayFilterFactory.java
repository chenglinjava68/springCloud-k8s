package com.weilus.gateway;

import com.feign.clients.OauthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
@ConditionalOnBean(OauthClient.class)
public class CheckTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckTokenGatewayFilterFactory.CheckTokenConfig> {
    private static final Logger log = LoggerFactory.getLogger(CheckTokenGatewayFilterFactory.class);
    @Autowired
    OauthClient oauthClient;

    public CheckTokenGatewayFilterFactory(){
        super(CheckTokenConfig.class);
    }

    @Override
    public GatewayFilter apply(CheckTokenConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = extractToken(request);
            if(StringUtils.isEmpty(token)){
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(ErrorToekn.REQUIRED_TOKEN));
            }
            Map<String,String> map = oauthClient.checkToken(token);
            if (map.containsKey("error")) {
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(ErrorToekn.ERROR_TOEKN));
            }else {
                Set<String> scope = extractScope(map);
                String serviceId = StringUtils.tokenizeToStringArray(request.getURI().getRawPath(), "/")[0];
                log.debug("检查能否访问域 {} , 当前支持访问 {}",serviceId,scope);
                if(scope.contains(serviceId)){
                    return chain.filter(exchange);
                }else {
                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .body(BodyInserters.fromObject(ErrorToekn.ERROR_SCOPE));
                }
            }
            return Mono.empty();
        };
    }

    public static class CheckTokenConfig{

    }

    public enum ErrorToekn{
        REQUIRED_TOKEN("需要token"),
        ERROR_TOEKN("请登录"),
        ERROR_SCOPE("不能访问服务")
        ;
        private String msg;
        ErrorToekn(String msg){this.msg = msg;}
        public String getMsg() {
            return msg;
        }
    }

    private String extractToken(ServerHttpRequest request){
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }else {
            return request.getQueryParams().getFirst("access_token");
        }
    }


    private Set<String> extractScope(Map<String, ?> map) {
        Set<String> scope = Collections.emptySet();
        if (map.containsKey("scope")) {
            Object scopeObj = map.get("scope");
            if (String.class.isInstance(scopeObj)) {
                scope = new LinkedHashSet<>(Arrays.asList(String.class.cast(scopeObj).split(" ")));
            } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
                @SuppressWarnings("unchecked")
                Collection<String> scopeColl = (Collection<String>) scopeObj;
                scope = new LinkedHashSet<>(scopeColl);	// Preserve ordering
            }
        }
        return scope;
    }
}
