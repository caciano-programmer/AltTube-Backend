FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD /target/comments-0.0.1-SNAPSHOT.jar comments.jar
RUN sh -c 'touch /comments.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=prod","comments.jar"]