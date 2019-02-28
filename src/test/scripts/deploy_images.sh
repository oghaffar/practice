#!/bin/bash

stackName=practiceIT
expectedReplicas=9

mvn dockerfile:build

docker stack deploy -c docker-compose.yml ${stackName}

while [ `docker stack ps ${stackName} | grep -E 'Running [0-9]+ [a-z]+ ago' | wc -l` -lt $expectedReplicas ]
do
    echo sleeping for 2 seconds to allow all $expectedReplicas containers to be running
    sleep 2
done




