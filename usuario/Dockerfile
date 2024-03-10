FROM openjdk:11
VOLUME /tmp
ADD ./target/usuario-0.0.1-SNAPSHOT.jar usuario.jar
ENTRYPOINT ["java","-jar","/usuario.jar"]