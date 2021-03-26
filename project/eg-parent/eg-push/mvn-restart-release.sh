#!/usr/bin/env bash
mvn clean package  && docker run -d --name eg-push -v /opt/crt/:/opt/crt/ -v /etc/localtime:/etc/localtime -v /home:/home -v /var/log/:/var/log/ -e params="-Dservice.type=release" -p 1140:1140 localhost:5000/eg-push:1.0