
FROM gradle:8.0-jdk17 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew /app/
COPY gradle /app/gradle
COPY src /app/src
RUN ./gradlew clean build --refresh-dependencies
RUN ./gradlew test --info
RUN ./gradlew execute-bdd-tests --info
RUN gradle clean build --no-daemon
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/bff-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]