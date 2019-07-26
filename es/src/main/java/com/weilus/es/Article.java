package com.weilus.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;


/**
 * @program test-springBoot
 * @date 2019-07-24 15:18
 **/
@Document(indexName="test",type="article",shards=2,replicas=0)
public class Article implements Serializable {
    @Id
    private Long id;
    /**
     * 标题
     */
    @Field(type = FieldType.Text,searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String title;
    /**
     * 内容
     */
    @Field(type = FieldType.Text)
    private String content;
    /**
     * 发表时间
     */
    @Field(type = FieldType.Date)
    private Date postTime;
    /**
     * 作者
     */
    @Field(type = FieldType.Text)
    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postTime=" + postTime +
                ", author='" + author + '\'' +
                '}';
    }
}

