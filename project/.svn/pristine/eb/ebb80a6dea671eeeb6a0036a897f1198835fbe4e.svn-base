#!/usr/bin/env bash
mvn clean package  && docker run -d --name news-service-web -v /etc/localtime:/etc/localtime -e params="-Dservice.type=test" -p 1123:1123 localhost:5000/news-service-web:1.0