FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD authorization-service-web-1.0.jar /root
ENV params=""
ENTRYPOINT ["sh","-c","java $params -Duser.timezone=GMT+08 -jar /root/authorization-service-web-1.0.jar"]