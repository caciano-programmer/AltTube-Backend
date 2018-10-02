FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD /target/account-0.0.1-SNAPSHOT.jar account.jar
RUN sh -c 'touch /account.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=prod","account.jar"]