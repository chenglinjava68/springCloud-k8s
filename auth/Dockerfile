FROM openjdk:8-jre-alpine
MAINTAINER 646154945@qq.com
ADD target/auth-1.1.jar /app.jar
VOLUME ["/data/logs"]
EXPOSE 8080
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar