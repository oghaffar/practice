FROM openjdk:8-jre

MAINTAINER Omar Ghaffar <omar.ghaffar@imanage.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/practice/practice.jar"]

EXPOSE 4567

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/practice/practice.jar
