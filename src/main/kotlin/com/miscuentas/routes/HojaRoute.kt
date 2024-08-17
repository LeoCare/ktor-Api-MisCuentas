package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.HojaCrearDto
import com.miscuentas.dto.HojaDto
import com.miscuentas.errors.HojaErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.hojas.HojaService
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

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todas las hojas
            get({
                description = "Obtener todas las hojas (Necesario Token)"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de hojas."
                        body<List<HojaDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron hojas."
                        body<String> {}
                    }
                }
            }) {
                hojaService.getAllHojas().mapBoth(
                    success = { hojas -> call.respond(HttpStatusCode.OK, hojas.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.NotFound, handleHojaError(error)) }
                )
            }

            // Obtener hoja por ID
            get("/{id}", {
                description = "Obtener hoja por ID (Necesario Token)"
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
                }
            }) {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    hojaService.getHojaById(id).mapBoth(
                        success = { hoja -> call.respond(HttpStatusCode.OK, hoja.toDto()) },
                        failure = { error -> call.respond(HttpStatusCode.NotFound, handleHojaError(error)) }
                    )
                } else {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                }
            }

            // Crear nueva hoja
            post({
                description = "Crear nueva hoja (Necesario Token)"
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
                }
            }) {
                val hojaCrearDto = call.receive<HojaCrearDto>()
                hojaService.addHoja(hojaCrearDto.toModel()).mapBoth(
                    success = { hoja -> call.respond(HttpStatusCode.Created, hoja.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.BadRequest, handleHojaError(error)) }
                )
            }

            // Actualizar una hoja
            put({
                description = "Actualizar una hoja (Necesario Token)"
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
                }
            }) {
                val hojaDto = call.receive<HojaDto>()
                hojaService.updateHoja(hojaDto.toModel()).mapBoth(
                    success = { hoja -> call.respond(HttpStatusCode.OK, hoja.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.BadRequest, handleHojaError(error)) }
                )
            }

            // Eliminar una hoja
            delete("/{id}", {
                description = "Eliminar una hoja por ID (Necesario Token)"
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
                }
            }) {
                logger.debug { "Delete hoja" }

                try {
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
