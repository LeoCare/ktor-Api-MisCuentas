package com.miscuentas.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors() {
    install(CORS) {
        anyHost() // Allow from any host
        allowHeader(HttpHeaders.ContentType) // Allow Content-Type header
        allowHeader(HttpHeaders.Authorization)
        allowHost("client-host") // Allow requests from client-host
    }
}