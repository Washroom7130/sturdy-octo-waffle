# Use OpenJDK as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file into the container
COPY ./demo-0.0.1-SNAPSHOT.war app.war

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.war"]
