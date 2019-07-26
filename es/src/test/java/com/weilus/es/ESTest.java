package com.weilus.es;

import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.Date;
import java.util.Iterator;

/**
 * @author 刘太全
 * @program springCloud-k8s
 * @date 2019-07-26 12:06
 **/
public class ESTest extends AppTest{
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    ArticleRepository repository;

    /**
     * 创建索引
     */
    @Test
    public void createIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(Article.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(Article.class);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex("test");
    }

    @Test
    public void testAdd(){
        Article art = new Article();
        art.setId(1L);
        art.setTitle("中国商人在沙特唐人街开中餐饭店");
        art.setPostTime(new Date());
        art.setAuthor("admin");
        art.setContent("这是内容。11111111111111111.。。。。。。。。。。。。。。");
        repository.save(art);
    }

    @Test
    public void testsearch(){
        Iterable<Article> iterable = repository.search(QueryBuilders.matchQuery("title","共和国"));
        Iterator<Article> it= iterable.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
