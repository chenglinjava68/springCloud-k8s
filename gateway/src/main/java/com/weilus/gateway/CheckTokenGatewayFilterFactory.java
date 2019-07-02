package com.weilus.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feign.clients.OauthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
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
                return response(exchange,ErrorToekn.REQUIRED_TOKEN);
            }
            Map<String,String> map = oauthClient.checkToken(token);
            if (map.containsKey("error")) {
                return response(exchange, ErrorToekn.ERROR_TOEKN);
            }else {
                Set<String> scope = extractScope(map);
                String serviceId = StringUtils.tokenizeToStringArray(request.getURI().getRawPath(), "/")[0];
                log.debug("检查能否访问域 {} , 当前支持访问 {}",serviceId,scope);
                if(scope.contains(serviceId)){
                    return chain.filter(exchange);
                }else {
                    return response(exchange,ErrorToekn.ERROR_SCOPE);
                }
            }
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

    private Mono response(ServerWebExchange exchange,ErrorToekn error){
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        byte[] resp = new byte[0];
        try {
            Map<String,String> err = new HashMap<>();
            err.put("code",error.name());
            err.put("msg",error.getMsg());
            resp = new ObjectMapper().writeValueAsBytes(err);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataBuffer bodyBuffer = response.bufferFactory().wrap(resp);//设置body
        return response.writeWith(Mono.just(bodyBuffer));
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
                scope = new LinkedHashSet<String>(Arrays.asList(String.class.cast(scopeObj).split(" ")));
            } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
                @SuppressWarnings("unchecked")
                Collection<String> scopeColl = (Collection<String>) scopeObj;
                scope = new LinkedHashSet<String>(scopeColl);	// Preserve ordering
            }
        }
        return scope;
    }
}
