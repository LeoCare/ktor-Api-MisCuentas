package com.miscuentas.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

/** Mecanisnmo de seguridad implementado en navegadores Web.
 * Permite o restringe solicitudes de origenes cruzados **/
fun Application.configureCors() {
    install(CORS) {
        anyHost() // Permitir cualquier host
        allowHeader(HttpHeaders.ContentType) // Permitir contenido en la cabecera
        allowHeader(HttpHeaders.Authorization)
        allowHost("client-host") // Permitir solicitudes del host-cliente

        /* OTRAS POSIBLES OPCIONES: */
        // Permitir solicitudes del host-cliente en puerto 8081:
        //allowHost("client-host:8081")

        // Permitir solicitudes del host-cliente en subdomains 'en', 'de' y 'es':
        //allowHost(
        //   "client-host",
        //   subDomains = listOf("en", "de", "es")
        //)

        // Permitir solicitudes del host-cliente en http y https:
        //allowHost("client-host", schemes = listOf("http", "https"))

        // Incluso restringir metodos:
        //allowMethod(HttpMethod.Put) // Permitir metodos PUT
        //allowMethod(HttpMethod.Delete)  // Permitir metodos DEL
    }
}