# springCloud

### kuberbetes 镜像仓库访问权限
```
kubectl create secret
docker-registry docker-registry-key
--docker-username=646154945@qq.com
--docker-password=[password]
--docker-email=646154945@qq.com
--docker-server=registry.cn-hangzhou.aliyuncs.com
-n weilus-cloud
```
### kubernetes 服务访问权限
```
kubectl create rolebinding default-view-binding \
--clusterrole=view \
--serviceaccount=weilus-cloud:default \
--namespace=weilus-cloud
```

### 基础设施 db redis
```
kubectl apply -f https://raw.githubusercontent.com/weilus923/springCloud-k8s/master/k8s-mysql-svc.yaml
kubectl apply -f https://raw.githubusercontent.com/weilus923/springCloud-k8s/master/k8s-redis-svc.yaml
```

### kubernetes 集中配置管理
> 1. spring-cloud-starter-kubernetes-config
    kubernetes configMap配置

> 2. 自动更新配置

```
    kubectl edit configmap feign-call -n weilus-cloud
```

### 用户中心 oauth2
```
kubectl apply -f https://raw.githubusercontent.com/weilus923/springCloud-k8s/master/auth/k8s.yaml
```
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
```
kubectl apply -f https://raw.githubusercontent.com/weilus923/springCloud-k8s/master/gateway/k8s.yaml
```
#### 更新路由
```
curl -H 'Content-Type: application/json' \
-X POST --data='{"uri":"lb://oauth","predicates":["Path=/oauth/**"],"filters":["StripPrefix=1"]}' \
http://10.96.10.96:8088/actuator/gateway/routes/oauth



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