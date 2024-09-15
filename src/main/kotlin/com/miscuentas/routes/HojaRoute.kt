package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.HojaCrearDto
import com.miscuentas.dto.HojaDto
import com.miscuentas.dto.UsuarioDto
import com.miscuentas.errors.HojaErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.auth.getAuthenticatedUsuario
import com.miscuentas.services.hojas.HojaService
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
private const val ENDPOINT = "/hojas"

fun Routing.hojaRoute() {

    val hojaService by inject<HojaService>()
    val usuarioService by inject<UsuarioService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todas las hojas
            get({
                description = "Obtener todas las hojas (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de hojas."
                        body<List<HojaDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron hojas."
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
                logger.debug { "Get hoja" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    hojaService.getAllHojas().mapBoth(
                        success = { hojas ->
                            call.respond(HttpStatusCode.OK, hojas.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handleHojaError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener las hojas.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener las hojas.")
                }
            }

            // Obtener hoja por ID
            get("/{id}", {
                description = "Obtener hoja por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID de la hoja."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Hoja encontrada."
                        body<HojaDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró la hoja."
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
                logger.debug { "Get hoja {id}" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        hojaService.getHojaById(id).mapBoth(
                            success = { hoja ->
                                call.respond(HttpStatusCode.OK, hoja.toDto())
                                      },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotFound, handleHojaError(error))
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener la hoja.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener la hoja.")
                }
            }

            // Obtencion de lista de hojas que coincidan con lo solicitado  --> GET /api/hojas/WhenData
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
                        description = "Retorna lista de hojas que coincidan con ese valor."
                        body<List<HojaDto>> { }
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
                        hojaService.getHojasBy(column, query).mapBoth(
                            success = { hojasCoincidentes ->
                                call.respond(HttpStatusCode.OK, hojasCoincidentes.toDto())
                            },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotImplemented, error.message)
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                    }


                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al buscar las hojas requeridas.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al buscar las hojas requeridas.")
                }
            }

            // Crear nueva hoja
            post({
                description = "Crear nueva hoja (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<HojaCrearDto> {}
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Hoja creada."
                        body<HojaDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al crear la hoja."
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
                logger.debug { "Post hoja" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@post

                    val hojaCrearDto = call.receive<HojaCrearDto>()
                    hojaService.addHoja(hojaCrearDto.toModel()).mapBoth(
                        success = { hoja ->
                            call.respond(HttpStatusCode.Created, hoja.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleHojaError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al crear la hoja.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear la hoja.")
                }
            }

            // Actualizar una hoja
            put({
                description = "Actualizar una hoja (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<HojaDto> {}
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Hoja actualizada."
                        body<HojaDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al actualizar la hoja."
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
                logger.debug { "Put hoja" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@put

                    val hojaDto = call.receive<HojaDto>()
                    hojaService.updateHoja(hojaDto.toModel()).mapBoth(
                        success = { hoja ->
                            call.respond(HttpStatusCode.OK, hoja.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleHojaError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar la hoja.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar la hoja.")
                }
            }

            // Eliminar una hoja
            delete("/{id}", {
                description = "Eliminar una hoja por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID de la hoja."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Hoja eliminada."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al eliminar la hoja."
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
                    HttpStatusCode.NotImplemented to {
                        description = "Retorna mensaje de error si la peticion a la BBDD falló."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Delete hoja" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@delete

                    // Recoge el id:
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        // Obtengo la Hoja segun id:
                        hojaService.getHojaById(id).onSuccess { hoja ->
                            //Elimino la hoja:
                            hojaService.deleteHoja(hoja).mapBoth(
                                success = { eliminada ->
                                    call.respond(HttpStatusCode.OK, "Hoja eliminada correctamente.")
                                          },
                                failure = { error ->
                                    call.respond(HttpStatusCode.BadRequest, handleHojaError(error))
                                }
                            )
                        }.onFailure { error ->
                            call.respond(HttpStatusCode.NotImplemented, handleHojaError(error))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al eliminar la hoja")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al eliminar la hoja")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleHojaError(error: Any) {
    when (error) {
        is HojaErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is HojaErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is HojaErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is HojaErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
