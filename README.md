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
kubectl apply -f pvc-mysql.yaml -f svc-mysql.yaml -f pvc-redis.yaml -f svc-redis.yaml
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
curl -X POST http://acme:acmesecret@10.96.10.96:8088/oauth/oauth/token \
-d 'grant_type=password&client_id=acme&username=liutaiq&password=123456'
```

#### 受信任的机构申请令牌
```
curl -X POST http://accc:acccsecret@10.96.10.96:8088/oauth/oauth/token \
-d 'grant_type=client_credentials'
```

#### 授权码申请令牌

> 引导用户授权
```
http://127.0.0.1:8080/oauth/authorize?client_id=acau&response_type=code&scope=user_info&redirect_uri=http://aa.ccdd
```

> 机构获取授权码; 申请令牌
```
curl -X POST http://acau:acausecret@10.96.10.96:8088/oauth/oauth/token \
-d 'grant_type=authorization_code&code=pg4Vz2&redirect_uri=http://aa.ccdd'
```

### 网关 gateway

    统一外网入口,限流,负载,降级

```
kubectl apply -f https://raw.githubusercontent.com/weilus923/springCloud-k8s/master/gateway/k8s.yaml
```
#### 配置路由
```
kubectl edit configmap gateway -n weilus-cloud
```

### 熔断 hystrix
```
kubectl apply -f https://raw.githubusercontent.com/weilus923/springCloud-k8s/master/feign-hystrix/k8s-feign-service.yaml
kubectl apply -f https://raw.githubusercontent.com/weilus923/springCloud-k8s/master/feign-hystrix/k8s-feign-call.yaml

curl -H 'Authorization: Bearer eb8ebbce-1ab0-4e2a-9427-0ffe0e384fc4' http://10.96.10.96:8088/feign-call/test/sayHello
```



### 微服务监控 admin

    spring-boot-admin发现eureka服务并监控;

    spring-boot-admin集成turbine; 监控熔断器


### 微服务云端开发
> 1. jenkins pipeline

  https://github.com/weilus923/jenkins

> 2. 微服务维护k8s

  k8s.yaml 扩容 缩容 滚动更新