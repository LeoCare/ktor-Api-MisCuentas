package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.EmailLogCrearDto
import com.miscuentas.dto.EmailLogDto
import com.miscuentas.errors.EmailLogErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.emaillogs.EmailLogService
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger {}
private const val ENDPOINT = "/emails"

fun Routing.emailLogRoute() {

    val emailLogService by inject<EmailLogService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todos los emails
            get({
                description = "Obtener todos los logs de emails (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de logs de emails."
                        body<List<EmailLogDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron logs de emails."
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
                logger.debug { "Get emails" }

                try {
                    emailLogService.getAllEmailLogs().mapBoth(
                        success = { emails ->
                            call.respond(HttpStatusCode.OK, emails.toDto())
                        },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handleEmailError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener los logs de emails.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener los logs de emails.")
                }
            }

            // Obtener email por ID
            get("/{id}", {
                description = "Obtener log de email por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del log de email."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Log de email encontrado."
                        body<EmailLogDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el log de email."
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
                logger.debug { "Get email {id}" }

                try {
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        emailLogService.getEmailLogById(id).mapBoth(
                            success = { email ->
                                call.respond(HttpStatusCode.OK, email.toDto())
                            },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotFound, handleEmailError(error))
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener el log de email.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener el log de email.")
                }
            }

            // Crear nuevo log de email
            post({
                description = "Crear nuevo log de email (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    body<EmailLogCrearDto> {}
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Log de email creado."
                        body<EmailLogDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al crear el log de email."
                        body<String> {}
                    }
                    HttpStatusCode.InternalServerError to {
                        description = "Retorna mensaje de error desconocido."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Post email" }

                try {
                    val emailLogCrearDto = call.receive<EmailLogCrearDto>()
                    emailLogService.addEmailLog(emailLogCrearDto.toModel()).mapBoth(
                        success = { email ->
                            call.respond(HttpStatusCode.Created, email.toDto())
                        },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleEmailError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al crear el log de email.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el log de email.")
                }
            }

            // Actualizar un log de email
            put({
                description = "Actualizar un log de email (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    body<EmailLogDto> {}
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Log de email actualizado."
                        body<EmailLogDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al actualizar el log de email."
                        body<String> {}
                    }
                    HttpStatusCode.InternalServerError to {
                        description = "Retorna mensaje de error desconocido."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Put email" }

                try {
                    val emailLogDto = call.receive<EmailLogDto>()
                    emailLogService.updateEmailLog(emailLogDto.toModel()).mapBoth(
                        success = { email ->
                            call.respond(HttpStatusCode.OK, email.toDto())
                        },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleEmailError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar el log de email.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar el log de email.")
                }
            }

            // Eliminar un log de email
            delete("/{id}", {
                description = "Eliminar un log de email por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del log de email."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Log de email eliminado."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al eliminar el log de email."
                        body<String> {}
                    }
                    HttpStatusCode.InternalServerError to {
                        description = "Retorna mensaje de error desconocido."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Delete email" }

                try {
                    // Recoge el id:
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        // Obtengo el log de email:
                        emailLogService.getEmailLogById(id).onSuccess { email ->
                            // Elimino el log de email:
                            emailLogService.deleteEmailLog(email).mapBoth(
                                success = { call.respond(HttpStatusCode.OK, "Log de email eliminado correctamente.") },
                                failure = { error -> call.respond(HttpStatusCode.BadRequest, handleEmailError(error)) }
                            )
                        }.onFailure { error ->
                            call.respond(HttpStatusCode.InternalServerError, handleEmailError(error))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al eliminar el log de email")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al eliminar el log de email")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleEmailError(error: Any) {
    when (error) {
        is EmailLogErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is EmailLogErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is EmailLogErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is EmailLogErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
