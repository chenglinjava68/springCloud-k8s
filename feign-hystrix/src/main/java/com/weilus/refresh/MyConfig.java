package com.weilus.refresh;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @program springCloud-k8s
 * @date 2019-06-26 17:08
 **/
@Configuration
@RefreshScope
public class MyConfig {

    @Value("${test.name:马云}")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
