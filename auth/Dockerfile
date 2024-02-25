FROM openjdk:11
VOLUME /tmp
ADD ./target/auth-0.0.1-SNAPSHOT.jar auth.jar
ENTRYPOINT ["java","-jar","/auth.jar"]