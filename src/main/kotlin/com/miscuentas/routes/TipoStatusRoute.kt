package com.miscuentas.routes


import com.github.michaelbull.result.mapBoth
import com.miscuentas.dto.TipoStatusDto
import com.miscuentas.errors.TipoStatusErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.services.tipoStatus.TipoStatusService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import mu.KotlinLogging
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger {}
private const val ENDPOINT = "/tipoStatus"

fun Routing.tipoStatusRoute() {

    val tipoStatusService by inject<TipoStatusService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todos los tipos de estado
            get({
                description = "Obtener todos los tipos de estado (Necesario Token)"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de tipos de estado."
                        body<List<TipoStatusDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron tipos de estado."
                        body<String> {}
                    }
                }
            }) {
                tipoStatusService.getAllTipoStatus().mapBoth(
                    success = { tipos -> call.respond(HttpStatusCode.OK, tipos.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.NotFound, handleTipoStatusError(error)) }
                )
            }

            // Obtener tipo de estado por código
            get("/{codigo}", {
                description = "Obtener tipo de estado por código (Necesario Token)"
                request {
                    pathParameter<String>("codigo") {
                        description = "Código del tipo de estado."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Tipo de estado encontrado."
                        body<TipoStatusDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el tipo de estado."
                        body<String> {}
                    }
                }
            }) {
                val codigo = call.parameters["codigo"]
                if (codigo != null) {
                    tipoStatusService.getTipoStatusById(codigo).mapBoth(
                        success = { tipo ->
                            call.respond(HttpStatusCode.OK, tipo.toDto())
                                  },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotFound, handleTipoStatusError(error))
                        }
                    )
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Código inválido.")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleTipoStatusError(error: Any) {
    when (error) {
        is TipoStatusErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is TipoStatusErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is TipoStatusErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is TipoStatusErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
