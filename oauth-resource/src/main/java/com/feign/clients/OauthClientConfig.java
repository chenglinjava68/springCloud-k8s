package com.feign.clients;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OauthClientConfig {
    @Bean
    @ConditionalOnProperty(value = {"feign.client.config.oauth.username","feign.client.config.oauth.password"})
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${feign.client.config.oauth.username}") String username,
            @Value("${feign.client.config.oauth.password}") String password){
        return new BasicAuthRequestInterceptor(username,password);
    }
}
