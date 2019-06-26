package com.weilus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by liutq on 2018/12/12.
 */
@Configuration
@ConditionalOnProperty(name = "spring.application.name",havingValue = "feign-service")
@RestController
public class Test2Controller {
    public static final Logger LOGGER = LoggerFactory.getLogger(Test2Controller.class);

    @RequestMapping(value="/api/sayHello",method= RequestMethod.POST)
    public String sayHello(HttpServletRequest request, @RequestBody Map<String, String> map){
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String headerName = enumeration.nextElement();
            String header = request.getHeader(headerName);
            LOGGER.info("feign-service header [{}] : {}",headerName,header);
        }
        return "feign-service: Hello "+map.get("name");
    }

    @RequestMapping(value="/api/hiMan",method=RequestMethod.POST)
    public String hiMan(@RequestBody Map<String, String> map){
        return "feign-service: Hi "+map.get("name");
    }

}
