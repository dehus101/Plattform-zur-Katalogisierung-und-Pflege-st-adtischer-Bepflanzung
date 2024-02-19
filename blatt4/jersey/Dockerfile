# syntax=docker/dockerfile:1
ARG JAVA_VERSION=21

FROM eclipse-temurin:${JAVA_VERSION}-jre
WORKDIR /app
COPY --chown=root:root build/libs/*.jar ./app.jar
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
