# Use a lightweight Java runtime image
FROM eclipse-temurin:21-jre

# Set working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/javarag-1.0.0 app.jar

# Expose port (adjust if you use another)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
