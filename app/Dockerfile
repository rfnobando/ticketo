# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . /app
WORKDIR /app

RUN mvn clean package -DskipTests

# Final stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/ticketo-0.0.1-SNAPSHOT.jar ticketo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ticketo.jar"]
