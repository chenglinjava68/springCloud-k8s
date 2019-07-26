### 创建库 test
```
curl -XPUT http://10.106.16.202:9200/test
```
### 删除库 test
```
curl -XDELETE http://10.106.16.202:9200/test
```
### 创建表结构 article
```
curl -XPUT http://10.106.16.202:9200/test/article/_mapping
{
    "properties" : {
        "id" : { "type" : "long" },
        "author" : {"type" : "text"},
        "content" : {"type" : "text"},
        "postTime" : { "type" : "long" },
        "title" : { "type": "text", "analyzer": "ik_max_word","search_analyzer": "ik_max_word"}
    }
}
```
### 查看表结构
```
curl -XGET http://192.168.1.38:30813/test/article/_mapping?pretty
```