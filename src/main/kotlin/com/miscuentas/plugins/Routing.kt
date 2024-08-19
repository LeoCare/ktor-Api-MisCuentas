package com.miscuentas.plugins

import com.miscuentas.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        usuarioRoute()
        balanceRoute()
        gastoRoute()
        hojaRoute()
        imagenRoute()
        pagoRoute()
        participanteRoute()
        tipoPerfilRoute()
        tipoStatusRoute()
        tipoBalanceRoute()
    }
}
