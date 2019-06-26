package com.weilus;

import com.feign.clients.FeignClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by liutq on 2018/12/12.
 */

@Configuration
@ConditionalOnProperty(name = "spring.application.name",havingValue = "feign-call")
@RestController
public class Test1Controller {
    public static final Logger LOGGER = LoggerFactory.getLogger(Test1Controller.class);
    @Autowired
    FeignClientService service;

    @RequestMapping("test/sayHello")
    public String sayHello(HttpServletRequest request){
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String headerName = enumeration.nextElement();
            String header = request.getHeader(headerName);
            LOGGER.info("feign-call header [{}] : {}",headerName,header);
        }
        return service.sayHello(Collections.singletonMap("name", "jhon"));
    }

    @RequestMapping("test/hiMan")
    public String hiMan(){
        return service.hiMan(Collections.singletonMap("name", "jhon"));
    }

}
