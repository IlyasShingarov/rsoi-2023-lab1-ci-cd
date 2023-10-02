FROM gradle:8.3-jdk20-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:20-jdk-slim AS image
EXPOSE 8080
RUN mkdir /app
WORKDIR app
ARG JAR_FILE=/home/gradle/src/build/libs/person-service-0.0.1.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}","/app/app.jar"]