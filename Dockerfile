FROM ubuntu:latest
LABEL authors="sandrang"

ENTRYPOINT ["top", "-b"]

# Use an appropriate base image, such as openjdk for a Spring Boot app
#FROM openjdk:17-jdk-slim
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy the project files into the container
COPY . .

# Make the mvnw file executable
RUN chmod +x ./mvnw

# Run Maven clean and package command
RUN ./mvnw clean package -DskipTests

# Expose the port the app will run on
EXPOSE 8080

# Set the start command
#CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "app.jar"]