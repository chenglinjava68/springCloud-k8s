package com.weilus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Created by liutq on 2018/12/11.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true,jsr250Enabled = true,proxyTargetClass=true)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    @Autowired
    SecurityMetadataSourcePropertity propertity;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        FilterSecurityPostProcessor processor = new FilterSecurityPostProcessor();
        processor.setPropertity(propertity);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        if(!CollectionUtils.isEmpty(propertity.getNoCheckToken())) {
            propertity.getNoCheckToken().forEach(s -> {
                HttpMethod httpmethod = null;
                String pattern = s;
                if (s.indexOf(" ") != -1) {
                    String[] arr = StringUtils.delimitedListToStringArray(s, " ");
                    httpmethod = HttpMethod.valueOf(arr[0]);
                    pattern = arr[1];
                }
                registry.antMatchers(httpmethod, pattern).permitAll();
            });
        }
        registry.withObjectPostProcessor(processor).anyRequest().authenticated();
    }
}
