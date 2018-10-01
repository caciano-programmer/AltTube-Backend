FROM anapsix/alpine-java:latest

VOLUME /tmp
ADD /account/target/account-0.0.1-SNAPSHOT.jar account.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/account.jar"]