FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD /target/video-0.0.1-SNAPSHOT.jar video.jar
RUN sh -c 'touch /video.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=prod","video.jar"]