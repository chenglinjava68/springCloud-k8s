FROM openjdk:8-jdk-alpine
MAINTAINER 646154945@qq.com
ADD target/oauth-resource.jar /app.jar
VOLUME ["/data/logs"]
EXPOSE 8081
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar