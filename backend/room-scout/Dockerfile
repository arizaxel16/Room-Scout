# Use a base image with OpenJDK 17
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the built JAR file into the container
COPY build/libs/room-scout-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for the backend service
EXPOSE 8080

# Command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]
