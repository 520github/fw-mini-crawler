#!/usr/bin/bash

cid=$(docker ps -a |grep -i lvdong-mysql | awk  '{print $1}')
if [ "$cid" != "" ]; then
    docker rm -f $cid
fi

docker run -d -p 3306:3306 \
           -v /Users/sunso520/home/docker/mysql/data:/var/lib/mysql:rw \
           -v /Users/sunso520/home/docker/mysql/log:/var/log/mysql:rw \
           -v $PWD/my.cnf:/etc/mysql/my.cnf:rw \
           -v /etc/localtime:/etc/localtime:ro \
           -e MYSQL_USER="lvdong" \
           -e MYSQL_PASSWORD="Ld123!@#" \
           -e MYSQL_ROOT_PASSWORD="Ld123!@#" \
           --restart always \
           --privileged=true \
           --name lvdong-mysql mysql:8.0.27