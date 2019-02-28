#!/bin/bash

stackName=practiceIT

docker container rm --force $(docker container ls -q)
docker image rm --force $(docker image ls -q)
docker stack rm ${stackName}

while [ `docker stack ps ${stackName} | wc -c` -ne 0 ]
do
    echo sleeping for 2 seconds to allow stack to be removed
    sleep 2
done

docker stack deploy -c docker-compose-db.yml ${stackName}

while [ `docker stack ps ${stackName} | grep -E 'Running [0-9]+ [a-z]+ ago' | wc -l` -lt 1 ]
do
    echo sleeping for 2 seconds to allow postgres to start
    sleep 2
done

mvn querydsl:export flyway:migrate




