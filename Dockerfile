FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/motormonitorv2-0.0.1-SNAPSHOT.jar /app/motormonitorv2.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/motormonitorv2.jar"]