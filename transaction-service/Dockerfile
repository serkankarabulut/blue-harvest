FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/transaction-service.jar transaction-service.jar
ENTRYPOINT ["java", "-jar", "/transaction-service.jar"]