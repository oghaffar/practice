#!/bin/bash

stackName=practiceIT
expectedReplicas=9

#docker container kill $(docker container ls | grep postgres | cut -c -12)

docker container rm --force $(docker container ls -q)
docker image rm -v --force $(docker image ls -q)
mvn dockerfile:build

docker stack rm ${stackName} &

wait $!

docker stack deploy -c docker-compose.yml ${stackName}

while [ `docker stack ps ${stackName} | grep -E 'Running [0-9]+ [a-z]+ ago' | wc -l` -lt $expectedReplicas ]
do
    echo sleeping for 2 seconds to allow all $expectedReplicas containers to be running
    sleep 2
done

sleep 1   # this sadness is needed because db is not responsive right after it has been started




