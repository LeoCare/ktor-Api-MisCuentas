# Con este archivo rearemos la imagen docker que contendra la app:

FROM gradle:7-jdk17 AS build
# Crearemos el directorio para la app y le copiaremos el archivo build.gradle
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

# Crearemos una nueva imagen ligera para usar la app.
FROM openjdk:17-jdk-slim-buster
EXPOSE 8080:8080
EXPOSE 8083:8083
# Directorio de almacenamiento
RUN mkdir /app
# Copiamos los certificados en el contenedor (solo si es necesario)
RUN mkdir /cert
COPY --from=build /home/gradle/src/cert/* /cert/
# Copiamos el archivo jar en el contenedor
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/ktor-Api-MisCuentas.jar
# Inicamos la app
ENTRYPOINT ["java","-jar","/app/ktor-Api-MisCuentas.jar"]