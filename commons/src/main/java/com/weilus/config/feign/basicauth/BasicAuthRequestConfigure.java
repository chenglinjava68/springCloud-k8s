package com.weilus.config.feign.basicauth;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicAuthRequestConfigure{

    @Bean
    @RefreshScope
    @ConditionalOnProperty(value = {"spring.security.user.username","spring.security.user.password"})
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${spring.security.user.username}") String username,
            @Value("${spring.security.user.password}") String password){
        return new BasicAuthRequestInterceptor(username,password);
    }

}
