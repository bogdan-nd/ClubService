FROM openjdk:14-jdk-alpine
ARG JAR_FILE=target/club-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]