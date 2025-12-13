# Build stage
FROM maven:3.9.11-eclipse-temurin-25 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM amazoncorretto:25-alpine AS runner
WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]
