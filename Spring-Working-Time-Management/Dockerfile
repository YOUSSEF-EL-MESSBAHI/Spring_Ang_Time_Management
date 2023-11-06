# Use the base image with Java
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app
# Copy the packaged Spring Boot application JAR file into the container
COPY target/miniProjetJee-0.0.1-SNAPSHOT.jar appWorkTime.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "appWorkTime.jar"]
