# Con este archivo rearemos la imagen docker que contendra la app:

# SE COMENTA LO NECESARIO PARA PRUEBAS EN INTERNO

FROM gradle:7-jdk17 AS build

# Crearemos el directorio para la app y le copiaremos el archivo build.gradle
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

# Crearemos una nueva imagen ligera para usar la app.
FROM openjdk:17-jdk-slim-buster
EXPOSE 8080:8080
#EXPOSE 8443:8443

# Directorio de almacenamiento
RUN mkdir /app

# Copiamos el archivo jar en el contenedor
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/ktor-api-miscuentas.jar

# Inicamos la app
ENTRYPOINT ["java","-jar","/app/ktor-api-miscuentas.jar"]
