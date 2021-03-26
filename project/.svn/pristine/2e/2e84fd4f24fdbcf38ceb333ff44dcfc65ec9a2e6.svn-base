#!/usr/bin/env bash
mvn clean package  && docker run -d --name mall-service-web -v /etc/localtime:/etc/localtime -e params="-Dservice.type=release" -p 1444:1444 localhost:5000/mall-service-web:1.0