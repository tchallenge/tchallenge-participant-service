FROM openjdk:8-jdk-alpine
LABEL maintainer="ilya.gubarev@gmail.com"
VOLUME /tmp
EXPOSE 4567
ARG JAR_FILE=build/libs/tchallenge-pilot-service.jar
ADD ${JAR_FILE} tchallenge-pilot-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tchallenge-pilot-service.jar"]
