package com.weilus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * Created by liutq on 2019/3/23.
 */
public class FilterSecurityPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
    public static final Logger LOG = LoggerFactory.getLogger(FilterSecurityPostProcessor.class);

    private SecurityMetadataSourcePropertity propertity;

    @Override
    public FilterSecurityInterceptor postProcess(FilterSecurityInterceptor interceptor) {
        interceptor.setAccessDecisionManager(accessDecisionManager());
        interceptor.setSecurityMetadataSource(securityMetadataSource());
        return interceptor;
    }

    public FilterInvocationSecurityMetadataSource securityMetadataSource(){
        return new DefaultFilterInvocationSecurityMetadataSource(propertity.getMetaSource());
    }

    public AccessDecisionManager accessDecisionManager(){
        return new AccessDecisionManager() {
            /**
             * @param authentication 包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities。
             * @param object 就是FilterInvocation对象，可以得到request等web资源。
             * @param configAttributes 是本次访问需要的权限。
             */
            @Override
            public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
                boolean match = configAttributes.stream().anyMatch(ca->
                        authentication.getAuthorities().stream()
                                .anyMatch(auth->auth.getAuthority().equals(ca.getAttribute()))
                );
                if(!match){
                    LOG.warn("当前用户拥有权限码: {}",authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                    LOG.warn("访问接口需要权限码: {}",configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList()));
                    throw new AccessDeniedException("当前访问没有权限");
                }
            }

            @Override
            public boolean supports(ConfigAttribute attribute) {
                return true;
            }

            @Override
            public boolean supports(Class<?> clazz) {
                return FilterInvocation.class.isAssignableFrom(clazz);
            }
        };
    }

    public SecurityMetadataSourcePropertity getPropertity() {
        return propertity;
    }

    public void setPropertity(SecurityMetadataSourcePropertity propertity) {
        this.propertity = propertity;
    }
}
