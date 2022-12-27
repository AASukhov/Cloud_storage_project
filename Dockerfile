FROM openjdk:18
VOLUME /tmp
EXPOSE 8080
ADD target/diploma-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]