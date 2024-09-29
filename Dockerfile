FROM openjdk:11-jdk-slim
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/Chomolungma-1.0.0.jar", "--spring.profiles.active=prod"]
