package com.miscuentas.plugins

import com.miscuentas.routes.usuarioRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        usuarioRoute()
    }
}
