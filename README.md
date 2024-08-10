# Ktor REST API Proyecto ciclo DAM

Rest Api para proyecto MisCuentas del ciclo DAM en MonteCastelo.

[![Kotlin](https://img.shields.io/badge/Code-Kotlin-blueviolet)](https://kotlinlang.org/)
[![LICENSE](https://img.shields.io/badge/Lisence-CC-%23e64545)](https://leocare.dev/docs/license/)
![GitHub](https://img.shields.io/github/last-commit/LeoCare/ktor-Api-MisCuentas)

![imagen](https://www.howtodoandroid.com/wp-content/uploads/2024/01/Kotlin-HTTP-client-For-Android.png)

- [Ktor REST API para proyecto MisCuentas](#ktor-rest-api-proyecto-ciclo-dam)
  - [Acerca de..](#acerca-de)
    - [APP Movil](#app-movil-miscuentas)
    - [APP Escritorio](#app-escritorio-todocuentas)
  - [Autor](#autor)
    - [Contacto](#contacto)
  - [Instalacion](#instalacion)
  - [Documentacion](docs/gfm/index.md)
  - [Uso](#uso)
  - [Contribucion](#contribucion)
  - [Licencia](#licencia)

## Acerca de..
<p>Este REST APi esta desarrollado para el proyecto de fin de ciclo 'MisCuentas'. Ciclo DAM cursado en el instituto <a rel="monteCastelo" href="https://www.fomento.edu/montecastelo/">MonteCastelo</a> de Vigo.<br>
El servicio WEB nos permite comunicarnos con nuestra BBDD MySql a traves del protocolo HTTP, recibiendo una respuesta en formato JSON.<br>
El objetivo de este servicio es dar soporte tanto a la aplicacion movil como de escritorio, ambas creadas como parte del mismo proyecto</p>

### Aplicaciones que forman parte de este proyecto:
- #### [APP Movil MisCuentas](https://github.com/LeoCare/MisCuentas)
- #### [APP Escritorio TodoCuentas](https://github.com/LeoCare/MisCuentas)

<a href="https://kotlinlang.org/" target="_blank"> <img loading="lazy" src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin_Icon.png/1200px-Kotlin_Icon.png" height="30"></a>
<a href="https://learn.microsoft.com/es-es/dotnet/csharp/" target="_blank"> <img loading="lazy" src="https://static-00.iconduck.com/assets.00/c-sharp-c-icon-1822x2048-wuf3ijab.png" height="30"></a><br>

Los proyectos tienen su repositorio en GitHub. 
<b>Para acceder a ellos no olvides solicitarme una clave de acceso al repositorio.</b> [Solicitar acceso](#contacto)

![image](https://1000logos.net/wp-content/uploads/2018/11/GitHub-logo.jpg)


## Autor
Mi nombre es <b>Leonardo David Care Prado</b>, soy tecnico en sistemas y desarrollador de aplicaciones multiplataforma, o eso espero con este proyecto...jjjjj.<br>
A fecha de este año (2024) llevo 4 años realizando trabajos de desarrollo para la misma empresa, ademas de soporte y sistemas.<br>
Estos desarrollos incluyen lenguajes como Html, C#, Xamarin, Oracle, Java y Kotlin.

[![Html](https://img.shields.io/badge/Code-Htmnl-blue)](https://www.w3schools.com/html/)
[![C#](https://img.shields.io/badge/Code-C_SHARP-green)](https://dotnet.microsoft.com/es-es/languages/csharp)
[![Xamarin](https://img.shields.io/badge/Code-Xamarin-red)](https://dotnet.microsoft.com/es-es/apps/xamarin)
[![Oracle](https://img.shields.io/badge/Code-Oracle-white)](https://www.oracle.com/es/)
[![Java](https://img.shields.io/badge/Code-Java-orange)](https://www.java.com/es/)
[![Kotlin](https://img.shields.io/badge/Code-Kotlin-blueviolet)](https://kotlinlang.org/)

 ### Contacto
Para cualquier consulta o aporte puedes comunicarte conmingo en el siguiente correo [leon1982care@gmail.com](https://mail.google.com/mail/u/0/?pli=1#inbox)

<p><a href="https://mail.google.com/mail/u/0/?pli=1#inbox" target="_blank">
        <img src="https://ams3.digitaloceanspaces.com/graffica/2021/06/logogmailgrafica-1-1024x576.png" 
    height="30" alt="correo_electronico">
</a></p>

## Instalacion
El proyecto esta desarrollado para ser subido a un contenedor Docker, al igual que la imagen de la BBDD en Mysql. Puedes ver los archivos docker-compose.yml y Dockerfile en la raiz del proyecto, para este proposito.<br>
De todas maneras, se puede ejecutar desde el IDE de java que utilices. Aqui te explico ambas maneras de poder hacer uso de este API REST.

<b>IMPORTANTE: este API utiliza conexion SSL con claves autogeneradas (no recomendado pero util en este caso), asi como claves de conexion a la BBDD y Token de acceso.<br>
Estos datos sencibles se almacenan en un archivo '.env' el cual se excluye en el repositorio, debes solicitarmelo y colocarlo en la raiz del proyecto!</b><br>
[Solicitar archivo .env](#contacto)...si ya lo tienes evita esto.

### DESPLEGAR SERVICIO EN DOCKER
Esta manera de deplegar el servicio dependerá de tener un contenedor, con la BBDD MySql, en la misma intancia de Docker.
  
#### Pasos:
1. En el archivo .env debes descomentar la linea con la etiqueta 'DB_URL=jdbc:mysql://...', tal como en la imagen:</b>
![Imagen del Proyecto](docs/imagenes/insta_docker_1.png)
<br><br>

2. Abre una terminal en el mismo IDE de Java y ejecuta este comando para generar el .jar del proyecto:
![Imagen del Proyecto](docs/imagenes/insta_docker_2.png)
>./gradlew buildFatJar

<br><br>
3. Luego, crea el contenedor del API ejecutando estes comando desde el servidor donde tengas docker. Para ello ubicate en la raiz de este proyecto y ejecuta:
![Imagen del Proyecto](docs/imagenes/insta_docker_3.png)
>docker-compose up

<br><br>
4. Una vez creados e iniciados ambos contenedores (API y MySql) colocales una red interna, ya que ambos deben compartir la misa red de la instancia de Docker. Para ello crea un rango de red en docker y asignalas a ambos contenedores.
   ![Imagen del Proyecto](docs/imagenes/insta_docker_4.png)
   <br><br>

6. Prueba la instalacion desde Postman (por ejemplo)
   ![Imagen del Proyecto](docs/imagenes/insta_docker_5.png)
>https://192.168.7.3:8443/usuario/registro.

<br><br>
### DESPLEGAR SERVICIO EN IDE

#### Pasos:
1. En el archivo .env debes descomentar la linea con la etiqueta <b>'DB_URL=jdbc:mysql://...'</b>
   ![Imagen del Proyecto](docs/imagenes/insta_ide_1.png)
   <br><br>
2. Ubicate en el archivo 'Application.kt' del proyecto y correlo (RUN).
   ![Imagen del Proyecto](docs/imagenes/insta_ide_2.png)
   <br><br>
3. Pruebalo desde Postman (por ejemplo)
   ![Imagen del Proyecto](docs/imagenes/insta_ide_3.png)
>https://127.0.0.1:8443/usuario/registro.

*Si algo falla, recuerda abrir los puertos asignados, revisa tu acceso a la bbdd o ten en cuenta que puedes acceder por Http(8080) o Https(8443).<br>
Tambien puedes modificar lo que sea necesario para que se ajuste a tus necesidades.

## Documentacion
La documentacion se realizó comentando todo el codigo, cada clase, ruta, repositorio y demas, con una sintaxis en concreto para ser reconocida por [Dokka](https://kotlinlang.org/docs/dokka-introduction.html).<br>
Esta libreria genera la documentacion en varios formatos, en mi caso se uso Markdown para acceder desde el mismo repositorio en el que estas.
Puedes ver y seguir toda la estructura de la aplicacion desde el siguiente enlace... [Ver](docs/gfm/index.md)

## Uso

## Contribucion

## Licencia
 This repository and all its contents are licensed under the **Creative Commons** license. If you want to know more, see the [LICENSE](https://joseluisgs.dev/docs/license/). Please cite the author if you share, use or modify this project, and use the same conditions for its educational, formative, or non-commercial use.

 <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">
JoseLuisGS</span>
by <a xmlns:cc="http://creativecommons.org/ns#" href="https://joseluisgs.dev/" property="cc:attributionName" rel="cc:attributionURL">
José Luis González Sánchez</a> is licensed under
a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons
Attribution-NonCommercial-ShareAlike 4.0 International License</a>.<br />Based on a work at
<a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/joseluisgs" rel="dct:source">https://github.com/joseluisgs</a>