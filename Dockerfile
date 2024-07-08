FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17.0.11_9-jdk AS dev
RUN mkdir /app
COPY --from=builder /app/target/*.jar /app/app.jar
ENV SERVER_PORT=8080
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","app.jar"]