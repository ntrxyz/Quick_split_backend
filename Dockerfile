# Step 1: Use official Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy all source code into the container
COPY . .

# Run the build to create the .jar file
RUN mvn clean package -DskipTests

# Step 2: Use a lighter image for running the app
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built .jar file from the build stage to the runtime stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port for the app
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
