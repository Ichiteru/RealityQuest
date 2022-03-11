FROM openjdk:17
EXPOSE 8080
ADD /web/target/reality-quest.jar reality-quest.jar
ENTRYPOINT ["java", "-jar", "/reality-quest.jar"]