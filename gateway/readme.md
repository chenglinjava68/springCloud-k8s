### 更新路由

```
    POST /actuator/gateway/routes/oauth HTTP/1.1
    Host: 127.0.0.1:8088
    Content-Type: application/json
    Cache-Control: no-cache
    Postman-Token: bd755abd-eb91-1868-856f-16027e386300

    {"uri":"lb://oauth","predicates":["Path=/oauth/**"],"filters":["StripPrefix=1"]}
```