FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/account-service.jar account-service.jar
ENTRYPOINT ["java", "-jar", "/account-service.jar"]