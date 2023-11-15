FROM amazoncorretto:11-alpine-jdk 
MAINTAINER AF 
COPY target/demo-0.0.1-SNAPSHOT.jar  skillfinder-app.jar    
ENTRYPOINT ["java", "-jar", "/skillfinder-app.jar"]  
