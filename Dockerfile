# --- Stage 1: Build the app using Maven ---
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy source code and Maven wrapper files
COPY . .

# Build the application (skipping tests if needed)
RUN mvn clean package -DskipTests

# --- Stage 2: Run with minimal JRE image ---
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=build /app/target/javarag-1.0.0.jar app.jar

# Expose app port
EXPOSE 8080

# Default command
ENTRYPOINT ["java", "-jar", "app.jar"]
