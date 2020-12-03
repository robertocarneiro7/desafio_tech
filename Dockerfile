FROM gradle:6.3-jdk13 as builder
WORKDIR /
COPY . /
RUN gradle build --no-daemon

FROM openjdk:13.0.2

COPY --from=builder /src/main/resources/log4j2-spring.xml ./
COPY --from=builder /build/libs/*.jar ./app.jar

ENTRYPOINT ["java","-Duser.timezone=America/Sao_Paulo","-jar","./app.jar"]