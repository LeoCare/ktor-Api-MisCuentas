package com.miscuentas.plugins

import io.ktor.server.application.*

fun Application.configureCors() {
    install(CORS) {
        anyHost() // Allow from any host
        allowHeader(HttpHeaders.ContentType) // Allow Content-Type header
        allowHeader(HttpHeaders.Authorization)
        allowHost("client-host") // Allow requests from client-host
    }
}