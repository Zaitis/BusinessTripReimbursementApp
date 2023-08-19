# Use official Maven image with Java 11 for building the application
FROM maven:3.6-jdk-11-slim AS build

# Set working directory in the container
WORKDIR /app

# Copy maven executable to the image
COPY ./pom.xml ./pom.xml

# Build the project (this step will also copy the H2 JAR file)
COPY ./src ./src

COPY ./src/main/resources/init.sql ./init.sql

RUN mvn clean package

# Start phase with Java 11 for running the application
FROM amazoncorretto:11
WORKDIR /app
COPY --from=build /app/target/BusinessTripReimbursementApp-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY --from=build /app/init.sql backend/src/main/resources/init.sql



# Run the application
ENTRYPOINT ["java", "-cp", "app.jar", "Main"]
