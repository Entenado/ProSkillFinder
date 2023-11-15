FROM amazoncorretto:17-alpine-jdk // de que imagen partimos
MAINTAINER AF // quien es el dueño de la imagen
COPY target/demo-0.0.1-SNAPSHOT.jar  skillfinder-app.jar    //va a copiar el empaquetado a github
ENTRYPOINT ["java", "-jar", "/skillfinder-app.jar"]  // es la instruccion que se va a ejecutar primero
