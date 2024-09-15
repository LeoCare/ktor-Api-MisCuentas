package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.GastoCrearDto
import com.miscuentas.dto.GastoDto
import com.miscuentas.dto.HojaDto
import com.miscuentas.errors.GastoErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.auth.getAuthenticatedUsuario
import com.miscuentas.services.gastos.GastoService
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
private const val ENDPOINT = "/gastos"

fun Routing.gastoRoute() {

    val gastoService by inject<GastoService>()
    val usuarioService by inject<UsuarioService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todos los gastos
            get({
                description = "Obtener todos los gastos (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de gastos."
                        body<List<GastoDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron gastos."
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
                logger.debug { "Get gastos" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    gastoService.getAllGastos().mapBoth(
                        success = { gastos ->
                            call.respond(HttpStatusCode.OK, gastos.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handleGastoError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener los gastos.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener los gastos.")
                }
            }

            // Obtener gasto por ID
            get("/{id}", {
                description = "Obtener gasto por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del gasto."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Gasto encontrado."
                        body<GastoDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el gasto."
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
                logger.debug { "Get gasto {id}" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        gastoService.getGastoById(id).mapBoth(
                            success = { gasto ->
                                call.respond(HttpStatusCode.OK, gasto.toDto())
                                      },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotFound, handleGastoError(error))
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener el gasto.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener el gasto.")
                }
            }

            // Obtencion de lista de gastos que coincidan con lo solicitado  --> GET /api/gastos/WhenData
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
                        description = "Retorna lista de gastos que coincidan con ese valor."
                        body<List<GastoDto>> { }
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
                        gastoService.getGastosBy(column, query).mapBoth(
                            success = { gastosCoincidentes ->
                                call.respond(HttpStatusCode.OK, gastosCoincidentes.toDto())
                            },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotImplemented, error.message)
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                    }


                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al buscar los gastos requeridas.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al buscar los gastos requeridas.")
                }
            }

            // Crear nuevo gasto
            post({
                description = "Crear nuevo gasto (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<GastoCrearDto> {}
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Gasto creado."
                        body<GastoDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al crear el gasto."
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
                logger.debug { "Post gasto" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@post

                    val gastoCrearDto = call.receive<GastoCrearDto>()
                    gastoService.addGasto(gastoCrearDto.toModel()).mapBoth(
                        success = { gasto -> call.respond(HttpStatusCode.Created, gasto.toDto()) },
                        failure = { error -> call.respond(HttpStatusCode.BadRequest, handleGastoError(error)) }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al crear el gasto.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el gasto.")
                }
            }

            // Actualizar un gasto
            put({
                description = "Actualizar un gasto (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    body<GastoDto> {}
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Gasto actualizado."
                        body<GastoDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al actualizar el gasto."
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
                logger.debug { "Put gasto" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@put

                    val gastoDto = call.receive<GastoDto>()
                    gastoService.updateGasto(gastoDto.toModel()).mapBoth(
                        success = { gasto ->
                            call.respond(HttpStatusCode.OK, gasto.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleGastoError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar el gasto.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar el gasto.")
                }
            }

            // Eliminar un gasto
            delete("/{id}", {
                description = "Eliminar un gasto por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del gasto."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Gasto eliminado."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al eliminar el gasto."
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
                logger.debug { "Delete gasto" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@delete

                    // Recoge el id:
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        // Obtengo Gasto segun id:
                        gastoService.getGastoById(id).onSuccess { gasto ->
                            // Elimino el gasto:
                            gastoService.deleteGasto(gasto).mapBoth(
                                success = { eliminado ->
                                    call.respond(HttpStatusCode.OK, "Gasto eliminado correctamente.")
                                          },
                                failure = { error ->
                                    call.respond(HttpStatusCode.BadRequest, handleGastoError(error))
                                }
                            )
                        }.onFailure { error ->
                            call.respond(HttpStatusCode.NotImplemented, handleGastoError(error))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al eliminar el gasto")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al eliminar el gasto")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleGastoError(error: Any) {
    when (error) {
        is GastoErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is GastoErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is GastoErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is GastoErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}

