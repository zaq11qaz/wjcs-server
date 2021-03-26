FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD user-service-web-1.0.jar /root
ENV params=""
ENTRYPOINT ["sh","-c","java $params -Duser.timezone=GMT+08 -jar /root/user-service-web-1.0.jar"]