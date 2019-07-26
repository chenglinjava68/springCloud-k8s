package com.weilus.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program springCloud-k8s
 * @date 2019-07-24 15:51
 **/
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    ArticleRepository repository;

    @GetMapping("test/add")
    public Object testAdd(){
        Article art = new Article();
        art.setId(1L);
        art.setTitle("中国商人在沙特唐人街开中餐饭店");
        art.setPostTime(new Date());
        art.setAuthor("admin");
        art.setContent("这是内容。11111111111111111.。。。。。。。。。。。。。。");
        repository.save(art);
        return art;
    }


    @GetMapping("test/get")
    public Object testGet(){
        return repository.findById(1L);
    }
}
