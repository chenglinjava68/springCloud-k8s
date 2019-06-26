# springCloud
### kubernetes权限
```
kubectl create rolebinding default-view-binding \
--clusterrole=view \
--serviceaccount=default:default \
--namespace=default
```
### 配置中心 config
> 1. docker安装rabbitMq
```
docker run -d --name=RABBITMQ -p 5672:5672 -p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest \
rabbitmq:3-management
```

> 2. 变更config配置,使配置自动生效
```
    version 1.5.x
    curl -X POST http://config:cf123456@192.168.198.1:7000/bus/refresh?destination=gateway:**

    version 2.x
    curl -v -X POST http://config:cf123456@192.168.198.1:7000/actuator/bus-refresh/gateway:**
```

> 3. 向eureka指定IP:  EUREKA_INSTANCE_IP-ADDRESS
```
    docker run -d -v /data/logs:/tmp/logs --name=config -e EUREKA_INSTANCE_IP-ADDRESS=192.168.198.128 -p 7000:7000 weiluscloud/config
```

### 用户中心 oauth2

#### 用户申请令牌
```
curl -X POST -d 'grant_type=password&client_id=acme&username=liutaiq&password=123456' \
http://acme:acmesecret@192.168.198.128:8088/oauth/oauth/token
```

#### 受信任的机构申请令牌
```
curl -X POST -d 'grant_type=client_credentials' \
http://accc:acccsecret@127.0.0.1:8080/oauth/token
```

#### 授权码申请令牌

> 引导用户授权
```
http://127.0.0.1:8080/oauth/authorize?client_id=acau&response_type=code&scope=user_info&redirect_uri=http://aa.ccdd
```

> 机构获取授权码; 申请令牌
```
curl -X POST -d 'grant_type=authorization_code&code=pg4Vz2&redirect_uri=http://aa.ccdd'  \
http://acau:acausecret@127.0.0.1:8080/oauth/token
```

### 网关 gateway

> 更新路由
```
    POST /actuator/gateway/routes/oauth HTTP/1.1
    Host: 127.0.0.1:8088
    Content-Type: application/json

    {"uri":"lb://oauth","predicates":["Path=/oauth/**"],"filters":["StripPrefix=1"]}
```
> 路由列表
```
    GET /actuator/gateway/routes
```

### 熔断 hystrix
> 1. docker启动服务提供者
```
docker run -d --name=feign-service \
-e "spring.application.name=feign-service" \
-e "server.port=8050" \
-e EUREKA_INSTANCE_IP-ADDRESS=192.168.198.128 \
weilus.cloud/feign-hystrix
```
> 2. docker启动服务消费者
```
docker run -d --name=feign-call \
-e "spring.application.name=feign-call" \
-e "server.port=8060" \
-e EUREKA_INSTANCE_IP-ADDRESS=192.168.198.128 \
weilus.cloud/feign-hystrix
```

### 微服务监控 admin

    spring-boot-admin发现eureka服务并监控;

    spring-boot-admin集成turbine; 监控熔断器


### 微服务云端开发
> 1. jenkins pipeline

  https://github.com/weilus923/jenkins

> 2. 微服务维护k8s

  k8s.yaml 扩容 缩容 滚动更新