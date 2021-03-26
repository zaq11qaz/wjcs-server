FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD cloud-zuul-1.0.jar /root
ENV params=""
ENTRYPOINT ["sh","-c","java $params -jar /root/cloud-zuul-1.0.jar"]