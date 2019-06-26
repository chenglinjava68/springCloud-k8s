FROM openjdk:8-jdk-alpine
MAINTAINER 646154945@qq.com
ADD target/gateway.jar /app.jar
VOLUME ["/data/logs"]
EXPOSE 8088
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar