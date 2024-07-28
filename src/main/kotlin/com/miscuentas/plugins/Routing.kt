package com.miscuentas.plugins

import com.miscuentas.services.tipoperfiles.TipoPerfilService
import com.miscuentas.services.usuarios.UsuarioService
import com.miscuentas.routes.usuarioRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting(
    usuarioService: UsuarioService = get(), //la funcion get() permite a Koin usar las di. Tambien puedo usar 'by inject()' en lugar de '= get()'
    tipoPerfilService: TipoPerfilService = get() //falta crear ruta para los perfiles
) {
    routing {
        usuarioRoute(usuarioService)
    }
}
