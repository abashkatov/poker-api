FROM gradle:6.8-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build --no-daemon
RUN ls -la /home/gradle/project

FROM openjdk:11-jre-slim
RUN mkdir /app
#RUN ls -la /home/gradle/project/build/
COPY --from=build /home/gradle/project/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]