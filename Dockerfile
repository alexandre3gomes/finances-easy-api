FROM openjdk:15-alpine as build
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:15-alpine
COPY --from=build app/spring-boot-loader .
COPY --from=build app/dependencies .
COPY --from=build app/snapshot-dependencies .
COPY --from=build app/application .
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]