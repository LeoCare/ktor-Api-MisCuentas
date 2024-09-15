package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.BalanceCrearDto
import com.miscuentas.dto.BalanceDto
import com.miscuentas.dto.HojaDto
import com.miscuentas.errors.BalanceErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.auth.getAuthenticatedUsuario
import com.miscuentas.services.balances.BalanceService
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
private const val ENDPOINT = "/balances"

fun Routing.balanceRoute() {

    val balanceService by inject<BalanceService>()
    val usuarioService by inject<UsuarioService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todos los balances
            get({
                description = "Obtener todos los balances (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de balances."
                        body<List<BalanceDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron balances."
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
                logger.debug { "Get balance" }

                try {
                    balanceService.getAllBalances().mapBoth(
                        success = { balances ->
                            call.respond(HttpStatusCode.OK, balances.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handleBalanceError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener los balances.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener los balances.")
                }
            }

            // Obtener balance por ID
            get("/{id}", {
                description = "Obtener balance por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del balance."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Balance encontrado."
                        body<BalanceDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el balance."
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
                logger.debug { "Get balance {id}" }

                try {
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        balanceService.getBalanceById(id).mapBoth(
                            success = { balance ->
                                call.respond(HttpStatusCode.OK, balance.toDto())
                                      },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotFound, handleBalanceError(error))
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al obtener el balance.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al obtener el balance.")
                }
            }

            // Obtencion de lista de balances que coincidan con lo solicitado  --> GET /api/balances/WhenData
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
                        description = "Retorna lista de balances que coincidan con ese valor."
                        body<List<BalanceDto>> { }
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
                        balanceService.getBalancesBy(column, query).mapBoth(
                            success = { balancesCoincidentes ->
                                call.respond(HttpStatusCode.OK, balancesCoincidentes.toDto())
                            },
                            failure = { error ->
                                call.respond(HttpStatusCode.NotImplemented, error.message)
                            }
                        )
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                    }


                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al buscar los balances requeridas.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al buscar los balances requeridas.")
                }
            }

            // Crear nuevo balance
            post({
                description = "Crear nuevo balance (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    body<BalanceCrearDto> {}
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Balance creado."
                        body<BalanceDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al crear el balance."
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
                logger.debug { "Post balance" }

                try {
                    val balanceCrearDto = call.receive<BalanceCrearDto>()
                    if(balanceCrearDto.monto.isBlank()){
                        call.respond(HttpStatusCode.BadRequest, "El monto es obligatorio.")
                        return@post
                    }
                    balanceService.addBalance(balanceCrearDto.toModel()).mapBoth(
                        success = { balance ->
                            call.respond(HttpStatusCode.Created, balance.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleBalanceError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al crear el balance.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el balance.")
                }
            }

            // Actualizar un balance
            put({
                description = "Actualizar un balance (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    body<BalanceDto> {}
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Balance actualizado."
                        body<BalanceDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al actualizar el balance."
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
                    val balanceDto = call.receive<BalanceDto>()
                    balanceService.updateBalance(balanceDto.toModel()).mapBoth(
                        success = { balance ->
                            call.respond(HttpStatusCode.OK, balance.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.BadRequest, handleBalanceError(error))
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar el balance.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar el balance.")
                }
            }

            // Eliminar un balance
            delete("/{id}", {
                description = "Eliminar un balance por ID (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
                        description = "ID del balance."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Balance eliminado."
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
                        description = "Error al eliminar el balance."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Delete balance" }

                try {
                    // Recoge el id:
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {

                        // Obtengo Balance segun id:
                        balanceService.getBalanceById(id).onSuccess { balance ->
                            // Elimino el balance:
                            balanceService.deleteBalance(balance).mapBoth(
                                success = { eliminado ->
                                    call.respond(HttpStatusCode.OK, "Balance eliminado correctamente.")
                                },
                                failure = { error ->
                                    call.respond(HttpStatusCode.BadRequest, handleBalanceError(error))
                                }
                            )
                        }.onFailure { error ->
                            call.respond(HttpStatusCode.NotImplemented, handleBalanceError(error))
                        }

                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al eliminar balance")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al eliminar balance")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleBalanceError(error: Any) {
    when (error) {
        is BalanceErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is BalanceErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is BalanceErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is BalanceErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
