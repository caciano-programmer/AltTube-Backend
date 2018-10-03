FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD /target/chat-0.0.1-SNAPSHOT.jar chat.jar
RUN sh -c 'touch /chat.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=prod","chat.jar"]