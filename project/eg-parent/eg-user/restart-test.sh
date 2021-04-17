#!/usr/bin/env bash
docker stop user-service-web && docker rm user-service-web && docker rmi localhost:5000/user-service-web:1.0 && docker rmi localhost:5000/user-service-web:latest && mvn clean install package  && docker run -d --name user-service-web -v /opt/crt/:/opt/crt/ -v /etc/localtime:/etc/localtime -v /var/log/:/var/log/ -e params="-Dservice.type=test" -e TZ=Asia/Shanghai  -p 1120:1120 localhost:5000/user-service-web:1.0