package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.miscuentas.dto.TipoPerfilDto
import com.miscuentas.errors.TipoPerfilErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.services.auth.getAuthenticatedUsuario
import com.miscuentas.services.tipoperfiles.TipoPerfilService
import com.miscuentas.services.usuarios.UsuarioService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger {}
private const val ENDPOINT = "/tipoPerfiles"

fun Routing.tipoPerfilRoute() {

    val tipoPerfilService by inject<TipoPerfilService>()
    val usuarioService by inject<UsuarioService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todos los tipos de perfil
            get({
                description = "Obtener todos los tipos de perfil (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de tipos de perfil."
                        body<List<TipoPerfilDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron tipos de perfil."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
                        body<String> {}
                    }
                    HttpStatusCode.InternalServerError to {
                        description = "Retorna mensaje de error desconocido."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Get tipoPerfil" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    tipoPerfilService.getAllTipoPerfiles().mapBoth(
                        success = { tipos ->
                            call.respond(HttpStatusCode.OK, tipos.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handleTipoPerfilError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener los tipoPerfil.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener los tipoPerfil.")
                }
            }

            // Obtener tipo de perfil por código
            get("/{codigo}", {
                description = "Obtener tipo de perfil por código (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<String>("codigo") {
                        description = "Código del tipo de perfil."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Tipo de perfil encontrado."
                        body<TipoPerfilDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el tipo de perfil."
                        body<String> {}
                    }
                    HttpStatusCode.NotImplemented to {
                        description = "Retorna mensaje de error si la peticion a la BBDD falló."
                        body<String> {}
                    }
                    HttpStatusCode.InternalServerError to {
                        description = "Retorna mensaje de error desconocido."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Get tipoPerfil {id}" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    val codigo = call.parameters["codigo"]
                    if (codigo != null) {
                        tipoPerfilService.getTipoPerfilById(codigo).mapBoth(
                            success = { tipo ->
                                call.respond(HttpStatusCode.OK, tipo.toDto())
                                      },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotFound, handleTipoPerfilError(error))
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Código inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener el tipoPerfil.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener el tipoPerfil.")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleTipoPerfilError(error: Any) {
    when (error) {
        is TipoPerfilErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is TipoPerfilErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is TipoPerfilErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is TipoPerfilErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
