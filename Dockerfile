FROM eclipse-temurin:19

MAINTAINER sugardaddy

COPY target/sugarbot-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]