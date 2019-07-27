package com.weilus.es;

import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        List<Article> list = new ArrayList<>();
//        Article art = new Article();
//        art.setId(1L);
//        art.setTitle("中国商人在沙特唐人街开中餐饭店");
//        art.setPostTime(new Date());
//        art.setAuthor("admin");
//        art.setContent("这是内容。中国商人在沙特唐人街开中餐饭店");
//        list.add(art);

//        Article art1 = new Article();
//        art1.setId(2L);
//        art1.setTitle("沙特阿拉伯被汽车炸弹袭击");
//        art1.setPostTime(new Date());
//        art1.setAuthor("admin");
//        art1.setContent("沙特阿拉伯被汽车炸弹袭击。。。。这是一篇文章");
//        list.add(art1);

//        Article art2 = new Article();
//        art2.setId(3L);
//        art2.setTitle("美国总统访问阿拉斯加州");
//        art2.setPostTime(new Date());
//        art2.setAuthor("admin");
//        art2.setContent("美国总统访问阿拉斯加州。。。。这是一篇文章");
//        list.add(art2);

//        Article art3 = new Article();
//        art3.setId(4L);
//        art3.setTitle("张学友在美国唐人街开餐馆");
//        art3.setPostTime(new Date());
//        art3.setAuthor("admin");
//        art3.setContent("明星开餐馆了");
//        list.add(art3);

//        repository.saveAll(list);
    }

    @Test
    public void testsearch(){
        Iterable<Article> iterable = repository.search(QueryBuilders.matchQuery("title","唐人街"));
        Iterator<Article> it= iterable.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
