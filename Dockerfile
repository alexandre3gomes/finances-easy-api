FROM maven:3.6.3-jdk-11-slim as build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn dependency:go-offline -B
RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
FROM openjdk:11-slim
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-cp","app:app/lib/*","net.finance.config.FinancesEasyApplication"]