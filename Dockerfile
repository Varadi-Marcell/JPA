FROM eclipse-temurin:17-jre-alpine

COPY target/ticket-backend-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java", "-jar", "/app.jar"]
