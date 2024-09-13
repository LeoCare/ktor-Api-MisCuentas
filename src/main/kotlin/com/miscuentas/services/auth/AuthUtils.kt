package com.miscuentas.services.auth

import com.github.michaelbull.result.getOrElse
import com.miscuentas.models.Usuario
import com.miscuentas.services.usuarios.UsuarioService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*


// Función para obtener el ID de usuario desde el token JWT
suspend fun PipelineContext<Unit, ApplicationCall>.getAuthenticatedUserId(): Long? {
    val userId = call.principal<JWTPrincipal>()
        ?.payload?.getClaim("userId")
        ?.asString()
        ?.toLongOrNull()

    if (userId == null) {
        call.respond(HttpStatusCode.Unauthorized, "Usuario no autenticado")
    }
    return userId
}

// Función para obtener el usuario autenticado a partir del token JWT
suspend fun PipelineContext<Unit, ApplicationCall>.getAuthenticatedUsuario(usuarioService: UsuarioService): Usuario? {
    val userId = getAuthenticatedUserId() ?: return null

    // Busca el usuario en la base de datos usando el userId
    return usuarioService.getUsuarioById(userId).getOrElse {
        call.respond(HttpStatusCode.NotFound, "Usuario de la petición no encontrado en el sistema.")
        null
    }
}
