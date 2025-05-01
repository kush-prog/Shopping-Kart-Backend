# Use the Eclipse Temurin JDK 23 Alpine image as the base
# https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:23-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project to the working directory in the container
COPY . ./

# Give execution permission to the Maven wrapper
RUN chmod +x mvnw

# Build the application using Maven (with dependency listing and skipping tests)
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

# Expose port 8080 for the application (Spring Boot default)
EXPOSE 8080

# Run the app, dynamically selecting the JAR file from the target directory
CMD ["sh", "-c", "java -jar target/*.jar"]
