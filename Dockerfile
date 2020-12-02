FROM openjdk:13.0.2
VOLUME /tmp
ARG PROFILE
COPY target/desafio_tech-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]