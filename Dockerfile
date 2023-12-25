#FROM openjdk:17-slim
#
#VOLUME /tmp
#ADD ./build/libs/*.jar app.jar
#ENV JAVA_OPTS=JAVA_OPTS
#
#EXPOSE 8080
#EXPOSE 5007
#
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]


FROM gradle:jdk17-jammy AS build

COPY src /home/app/src
COPY gradle /home/app/gradle
COPY build.gradle /home/app
COPY settings.gradle /home/app

RUN gradle --project-dir "/home/app" clean build -x pmdMain -x checkstyleMain -x spotbugsMain

FROM openjdk:17-slim

COPY --from=build /home/app/build/libs/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -Dkograf.upload.dir=/home/uploads -jar /app.jar" ]