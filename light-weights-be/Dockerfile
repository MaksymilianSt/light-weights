FROM eclipse-temurin:21-alpine

ENV PORT=8080

WORKDIR /app

COPY target/light-weights-be*.jar /app/light-weights-be.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/light-weights-be.jar"]
