### 用户申请令牌

curl -X POST -d 'grant_type=password&client_id=acme&username=liutaiq&password=123456' \
http://acme:acmesecret@192.168.198.1:8080/oauth/token


### 受信任的机构申请令牌
curl -X POST -d 'grant_type=client_credentials' \
http://accc:acccsecret@192.168.198.1:8080/oauth/token


### 授权码申请令牌

> 引导用户授权
http://127.0.0.1:8080/oauth/authorize?client_id=acau&response_type=code&scope=user_info&redirect_uri=http://aa.ccdd

> 机构获取授权码; 申请令牌
curl -X POST -d 'grant_type=authorization_code&code=UuP360&redirect_uri=http://aa.ccdd'  \
http://acau:acausecret@192.168.198.1:8080/oauth/token