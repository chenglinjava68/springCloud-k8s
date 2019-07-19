FROM openjdk:8-jre-alpine
MAINTAINER 646154945@qq.com
ADD target/feign-hystrix-1.0.jar /app.jar
VOLUME ["/data/logs"]
EXPOSE 8081
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar