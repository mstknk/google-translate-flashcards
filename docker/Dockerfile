FROM openjdk:11.0.1-jre-slim
VOLUME /tmp
COPY /google-translate-flashcards-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["/usr/bin/java","-jar","/app.jar"]