FROM openjdk:11
VOLUME /tmp
ADD ./target/pedido-0.0.1-SNAPSHOT.jar pedido.jar
ENTRYPOINT ["java","-jar","/pedido.jar"]