FROM openjdk:17-jdk-alpine
MAINTAINER joljolionn.com
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
