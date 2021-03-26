#!/usr/bin/env bash
mvn clean package  && docker run -d --name cloud-zuul -v /etc/localtime:/etc/localtime -v /var/log/:/var/log/ -e params="-Dservice.type=release" -p 8768:8768 localhost:5000/cloud-zuul:1.0