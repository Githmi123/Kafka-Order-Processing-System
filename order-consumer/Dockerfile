FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY order-consumer/pom.xml .
RUN mvn dependency:go-offline

COPY order-consumer/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]