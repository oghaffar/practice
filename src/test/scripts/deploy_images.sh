#!/bin/bash

stackName=practiceIT

DOCKER_HOST=tcp://192.168.206.130:2375
docker container rm --force $(docker container ls -q)
docker image rm --force $(docker image ls -q)
mvn dockerfile:build &

mvn1pid=$!

DOCKER_HOST=tcp://192.168.206.129:2375
docker container rm --force $(docker container ls -q)
docker image rm --force $(docker image ls -q)
mvn dockerfile:build &

mvn2pid=$!

DOCKER_HOST=tcp://192.168.206.128:2375
docker container rm --force $(docker container ls -q)
docker image rm --force $(docker image ls -q)
mvn dockerfile:build &

mvn3pid=$!

docker stack rm ${stackName} &

wait $!
wait $mvn1pid
wait $mvn2pid
wait $mvn3pid

docker stack deploy -c docker-compose.yml ${stackName} &
#wait $!


