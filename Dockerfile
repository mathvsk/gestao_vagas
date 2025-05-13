FROM ubuntu:latest as build
LABEL authors="mathe"

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk maven
COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]