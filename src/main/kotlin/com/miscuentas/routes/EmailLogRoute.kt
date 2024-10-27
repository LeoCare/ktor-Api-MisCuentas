package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.EmailLogCrearDto
import com.miscuentas.dto.EmailLogDto
import com.miscuentas.dto.HojaDto
import com.miscuentas.errors.EmailLogErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.auth.getAuthenticatedUsuario
import com.miscuentas.services.emaillogs.EmailLogService
import com.miscuentas.services.usuarios.UsuarioService
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
    val usuarioService by inject<UsuarioService>()

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

            // Obtencion de lista de emails que coincidan con lo solicitado  --> GET /api/emails/WhenData
            get("/WhenData", {
                description = "SOLICITAR UNOS DATOS EN CONCRETO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    queryParameter<String>("c") {
                        description = "nombre de la columna a filtrar"
                        required = true // Optional
                    }
                    queryParameter<String>("q") {
                        description = "dato de la columna a filtrar"
                        required = true // Optional
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna lista de emails que coincidan con ese valor."
                        body<List<EmailLogDto>> { }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> {}
                    }
                    HttpStatusCode.NotImplemented to {
                        description = "Retorna mensaje de error si la peticion a la BBDD falló."
                        body<String> { }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
                        body<String> {}
                    }
                    HttpStatusCode.InternalServerError to {
                        description = "Retorna mensaje de error desconocido."
                        body<String> {}
                    }
                    HttpStatusCode.Forbidden to {
                        description = "Acceso denegado por falta de permisos."
                        body<String> { }
                    }
                }
            }) {
                logger.debug { "Get WhenData" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    //Obtener datos que coincidan con..
                    val column = call.request.queryParameters["c"] //Con parametros pasado en la query
                    val query = call.request.queryParameters["q"]

                    //si especifica columna y dato:
                    if (!column.isNullOrEmpty() && !query.isNullOrEmpty()) {
                        emailLogService.getEmailLogsBy(column, query).mapBoth(
                            success = { emailsCoincidentes ->
                                call.respond(HttpStatusCode.OK, emailsCoincidentes.toDto())
                            },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotImplemented, error.message)
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                    }


                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al buscar los emails requeridas.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al buscar los emails requeridas.")
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
                        description = "OK"
                        body<String> {}
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
                            call.respond(HttpStatusCode.Created, "OK")
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
