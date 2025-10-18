# ---- Build Stage ----
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY . .

# Fix permission for mvnw
RUN chmod +x ./mvnw

# Build the Spring Boot jar
RUN ./mvnw clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar portfolio.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "portfolio.jar"]
