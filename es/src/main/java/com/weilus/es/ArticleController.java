package com.weilus.es;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author 刘太全
 * @program springCloud-k8s
 * @date 2019-07-26 14:48
 **/
@RestController
public class ArticleController {
    @Autowired
    ArticleRepository repository;

    @PostMapping("article")
    public Object add(@RequestBody Article art){
        repository.save(art);
        return Collections.singletonMap("op","succ");
    }

    @GetMapping("article")
    public Object search(Article art){
        Iterable<Article> iterable = repository.search(QueryBuilders.matchQuery("title",art.getTitle()));
        Iterator<Article> it= iterable.iterator();
        List<Article> res = new ArrayList<>();
        while (it.hasNext()){
            res.add(it.next());
        }
        return res;
    }

}
