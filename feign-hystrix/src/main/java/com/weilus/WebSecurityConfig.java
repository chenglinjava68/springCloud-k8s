package com.weilus;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program springCloud
 * @date 2019-05-23 14:27
 **/
@EnableWebSecurity
public class WebSecurityConfig{

    @Configuration
    @Order(1)
    public static class ApiConfigurer extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http
                    .antMatcher("/api/**").authorizeRequests().anyRequest().hasRole("USER")
                    .and()
                    .antMatcher("/test/**").authorizeRequests().anyRequest().hasRole("USER")
                    .and()
                    .httpBasic();
        }
    }
}
