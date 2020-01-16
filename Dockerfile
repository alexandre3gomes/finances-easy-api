FROM maven:3.3-jdk-8 as build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn dependency:go-offline -B
RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
FROM openjdk:8-jdk-alpine
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-cp","app:app/lib/*","net.finance.config.FinancesEasyApplication"]