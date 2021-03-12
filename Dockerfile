FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/poker-api-*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]