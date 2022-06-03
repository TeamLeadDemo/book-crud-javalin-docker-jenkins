#base image 
FROM openjdk:latest

#copy the jar file of backend appln into the container in the name of app.jar
COPY target/book-management-system-javalin-maven-0.0.1-SNAPSHOT.jar app.jar

#run the jar file in the container
ENTRYPOINT ["java","-jar","/app.jar"]

