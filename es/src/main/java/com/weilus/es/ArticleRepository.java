package com.weilus.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 刘太全
 * @program test-springBoot
 * @date 2019-07-24 15:20
 **/
public interface ArticleRepository extends ElasticsearchRepository<Article,Long>{

}
