package com.miscuentas.routes

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.ParticipanteCrearDto
import com.miscuentas.dto.ParticipanteDto
import com.miscuentas.errors.ParticipanteErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.auth.getAuthenticatedUsuario
import com.miscuentas.services.participantes.ParticipanteService
import com.miscuentas.services.usuarios.UsuarioService
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger {}
private const val ENDPOINT = "/participantes"

fun Routing.participanteRoute() {

    val participanteService by inject<ParticipanteService>()
    val usuarioService by inject<UsuarioService>()

    route("/$ENDPOINT") {

        authenticate {
            // Obtener todos los participantes
            get({
                description = "Obtener todos los participantes (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de participantes."
                        body<List<ParticipanteDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron participantes."
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
                logger.debug { "Get participantes" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    participanteService.getAllParticipantes().mapBoth(
                        success = { participantes ->
                            call.respond(HttpStatusCode.OK, participantes.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handleParticipanteError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener los participantes.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener los participantes.")
                }
            }

            // Obtener participante por ID
            get("/{id}", {
                description = "Obtener participante por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del participante."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Participante encontrado."
                        body<ParticipanteDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el participante."
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
                logger.debug { "Get participante {id}" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        participanteService.getParticipanteById(id).mapBoth(
                            success = { participante ->
                                call.respond(HttpStatusCode.OK, participante.toDto())
                                      },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotFound, handleParticipanteError(error))
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener los participantes.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener los participantes.")
                }
            }

            // Crear nuevo participante
            post({
                description = "Crear nuevo participante (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<ParticipanteCrearDto> {}
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Participante creado."
                        body<ParticipanteDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al crear el participante."
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
                logger.debug { "Post participante" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@post

                    val participanteCrearDto = call.receive<ParticipanteCrearDto>()
                    if(participanteCrearDto.nombre.isBlank()){
                        call.respond(HttpStatusCode.BadRequest, "El nombre es obligatorio.")
                        return@post
                    }
                    participanteService.addParticipante(participanteCrearDto.toModel()).mapBoth(
                        success = { participante ->
                            call.respond(HttpStatusCode.Created, participante.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleParticipanteError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al crear el participante.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el participante.")
                }
            }

            // Actualizar un participante
            put({
                description = "Actualizar un participante (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<ParticipanteDto> {}
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Participante actualizado."
                        body<ParticipanteDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al actualizar el participante."
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
                logger.debug { "Put participante" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@put

                    val participanteDto = call.receive<ParticipanteDto>()
                    participanteService.updateParticipante(participanteDto.toModel()).mapBoth(
                        success = { participante ->
                            call.respond(HttpStatusCode.OK, participante.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleParticipanteError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar el participante.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar el participante.")
                }
            }

            // Eliminar un participante
            delete("/{id}", {
                description = "Eliminar un participante por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del participante."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Participante eliminado."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al eliminar el participante."
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
                }
            }) {
                logger.debug { "Delete participante" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@delete

                    // Recoge el id:
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {

                        // Obtengo el participante:
                        participanteService.getParticipanteById(id).onSuccess { participante ->

                            // Elimino el participante:
                            participanteService.deleteParticipante(participante).mapBoth(
                                success = { eliminado ->
                                    call.respond(HttpStatusCode.OK, "Participante eliminado correctamente.")
                                          },
                                failure = { error ->
                                    call.respond(HttpStatusCode.BadRequest, handleParticipanteError(error))
                                }
                            )
                        }.onFailure { error ->
                            call.respond(HttpStatusCode.NotImplemented, handleParticipanteError(error))
                        }

                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al eliminar participante")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al eliminar participante")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleParticipanteError(error: Any) {
    when (error) {
        is ParticipanteErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is ParticipanteErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is ParticipanteErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is ParticipanteErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}

