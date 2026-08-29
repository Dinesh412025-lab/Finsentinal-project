# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Render dynamically assigns a port via the PORT environment variable.
# Spring Boot automatically picks up the PORT env var.
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
