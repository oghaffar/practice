# practice

Docker Commands:

To build:
mvn clean compile assembly:single package dockerfile:build

To run:
docker run -d --name hi -p 4567:4567 oghaffar/practice:1.0

To remove all containers:
docker container rm $(docker container ls -aq) -f