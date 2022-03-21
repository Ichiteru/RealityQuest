FROM openjdk:17
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
#EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=container", "-jar", "/app.jar"]