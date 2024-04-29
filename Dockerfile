# Builder Stage
FROM maven:3.8.4-openjdk-17 as builder

WORKDIR /usr/src/app

COPY . .

# Run Maven to build the application
RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:17-jdk

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /usr/src/app/target/*.jar /app/app.jar

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
