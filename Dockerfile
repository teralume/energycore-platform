FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src src

RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S energycore && adduser -S energycore -G energycore

COPY --from=build /workspace/target/*.jar /app/energycore-platform.jar

USER energycore

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/energycore-platform.jar"]