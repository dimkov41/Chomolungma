FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/Chomolungma-1.0.0.jar /app/Chomolungma-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "Chomolungma-1.0.0.jar", "--spring.profiles.active=prod"]
