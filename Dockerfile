# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as build
EXPOSE 8080
CMD ["./mvnw", "spring-javaformat:apply", "spring-boot:run"]