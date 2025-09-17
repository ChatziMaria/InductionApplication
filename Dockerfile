FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

ENV JASYPT_ENCRYPTOR_PASSWORD=encrKey123
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java" ,"-Djava.security.egd=file:/dev/./urandom  -jar app.jar"]