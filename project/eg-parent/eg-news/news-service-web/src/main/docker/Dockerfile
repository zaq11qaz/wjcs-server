FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD news-service-web-1.0.jar /root
ENV params=""
ENTRYPOINT ["sh","-c","java $params -Duser.timezone=GMT+08 -Xms500m -Xmx1500m -jar /root/news-service-web-1.0.jar"]