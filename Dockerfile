FROM openjdk:22

ENV MYSQL_USER=root

COPY /target/menuguru-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]