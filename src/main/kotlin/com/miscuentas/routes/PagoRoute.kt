package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.HojaDto
import com.miscuentas.dto.PagoCrearDto
import com.miscuentas.dto.PagoDto
import com.miscuentas.errors.PagoErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.models.Pago
import com.miscuentas.services.auth.getAuthenticatedUsuario
import com.miscuentas.services.pagos.PagoService
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
private const val ENDPOINT = "/pagos"

fun Routing.pagoRoute() {

    val pagoService by inject<PagoService>()
    val usuarioService by inject<UsuarioService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todos los pagos
            get({
                description = "Obtener todos los pagos (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de pagos."
                        body<List<PagoDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron pagos."
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
                logger.debug { "Get pago" }

                    try {
                        // Recoge Id del token y lo valida:
                        val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                        pagoService.getAllPagos().mapBoth(
                        success = { pagos ->
                            call.respond(HttpStatusCode.OK, pagos.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handlePagoError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener el pago.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener el pago.")
                }
            }

            // Obtener pago por ID
            get("/{id}", {
                description = "Obtener pago por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del pago."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Pago encontrado."
                        body<PagoDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el pago."
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
                logger.debug { "Get pago {id}" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        pagoService.getPagoById(id).mapBoth(
                            success = { pago ->
                                call.respond(HttpStatusCode.OK, pago.toDto())
                                      },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotFound, handlePagoError(error))
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener el pago.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener el.")
                }
            }

            // Obtencion de lista de pagos que coincidan con lo solicitado  --> GET /api/pagos/WhenData
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
                        description = "Retorna lista de pagos que coincidan con ese valor."
                        body<List<PagoDto>> { }
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
                        pagoService.getPagosBy(column, query).mapBoth(
                            success = { pagosCoincidentes ->
                                call.respond(HttpStatusCode.OK, pagosCoincidentes.toDto())
                            },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotImplemented, error.message)
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                    }


                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al buscar los pagos requeridas.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al buscar los pagos requeridas.")
                }
            }

            // Crear nuevo pago
            post({
                description = "Crear nuevo pago (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<PagoCrearDto> {}
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Pago creado."
                        body<PagoDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al crear el pago."
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
                logger.debug { "Post pago" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@post

                    val pagoCrearDto = call.receive<PagoCrearDto>()
                    pagoService.addPago(pagoCrearDto.toModel()).mapBoth(
                        success = { pago ->
                            call.respond(HttpStatusCode.Created, pago.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handlePagoError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al crear el pago.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el pago.")
                }
            }

            // Actualizar un pago
            put({
                description = "Actualizar un pago (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<PagoDto> {}
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Pago actualizado."
                        body<PagoDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al actualizar el pago."
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
                logger.debug { "Put pago" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@put

                    val pagoDto = call.receive<PagoDto>()
                    pagoService.updatePago(pagoDto.toModel()).mapBoth(
                        success = { pago ->
                            call.respond(HttpStatusCode.OK, pago.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handlePagoError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar el pago.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar el pago.")
                }
            }

            // Eliminar un pago
            delete("/{id}", {
                description = "Eliminar un pago por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del pago."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Pago eliminado."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al eliminar el pago."
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
                logger.debug { "Delete pago" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@delete

                    // Recoge el id:
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        // Obtengo Pago segun id:
                        pagoService.getPagoById(id).onSuccess { pago ->
                            // Elimino el gasto:
                            pagoService.deletePago(pago).mapBoth(
                                success = { call.respond(HttpStatusCode.OK, "Pago eliminado correctamente.") },
                                failure = { error -> call.respond(HttpStatusCode.BadRequest, handlePagoError(error)) }
                            )
                        }.onFailure { error ->
                            call.respond(HttpStatusCode.NotImplemented, handlePagoError(error))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al eliminar el pago")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al eliminar el pago")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handlePagoError(error: Any) {
    when (error) {
        is PagoErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is PagoErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is PagoErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is PagoErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
