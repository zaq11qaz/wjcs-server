#!/usr/bin/env bash
mvn clean package  && docker run -d --name message-service-web -v /etc/localtime:/etc/localtime -e params="-Dservice.type=test" -p 1150:1150 localhost:5000/message-service-web:1.0