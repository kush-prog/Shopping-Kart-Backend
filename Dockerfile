# Stage 1: Build the app using Maven wrapper
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the Maven wrapper and config first (to leverage Docker cache)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Ensure mvnw has the correct executable permission
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app using JDK 17
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
