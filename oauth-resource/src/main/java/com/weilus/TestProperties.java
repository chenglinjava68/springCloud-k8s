package com.weilus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author 刘太全
 * @program springCloud
 * @date 2019-05-22 13:39
 **/
@Configuration
@RefreshScope
public class TestProperties {

    @Value("${test.abc.name}")
    private String name;

    @Value("${test.abc.age}")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
