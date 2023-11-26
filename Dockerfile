FROM openjdk:17-slim

VOLUME /tmp
ADD ./build/libs/*.jar app.jar
ENV JAVA_OPTS=JAVA_OPTS

EXPOSE 8080
EXPOSE 5007

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]