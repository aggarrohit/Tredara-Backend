FROM openjdk:17
COPY target/*.jar app.jar
RUN mkdir /images
ENTRYPOINT ["java","-jar","/app.jar"]