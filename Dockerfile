# Step 1: Use an official JDK image
FROM eclipse-temurin:17-jdk

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy the built JAR into the image
COPY target/*.jar portfolio.jar

# Step 4: Expose port 8080 (optional, for clarity)
EXPOSE 8080

# Step 5: Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "portfolio.jar"]
