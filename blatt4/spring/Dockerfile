# syntax=docker/dockerfile:1
ARG JAVA_VERSION=21

FROM eclipse-temurin:${JAVA_VERSION}-jre AS base
WORKDIR /app

FROM base as build
COPY build/libs/*.jar ./app.jar
RUN ["java", "-Djarmode=layertools", "-jar", "app.jar", "extract"]

FROM base as run
COPY --from=build /app/dependencies/ ./
COPY --from=build /app/spring-boot-loader/ ./
COPY --from=build /app/snapshot-dependencies/ ./
COPY --from=build /app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
