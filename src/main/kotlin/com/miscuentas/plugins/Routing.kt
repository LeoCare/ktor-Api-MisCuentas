package com.miscuentas.plugins

import com.miscuentas.services.tipoperfiles.TipoPerfilService
import com.miscuentas.services.usuarios.UsuarioService
import com.miscuentas.routes.usuarioRoute
import com.miscuentas.services.auth.TokensService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    routing {
        usuarioRoute()
    }
}
