package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.miscuentas.dto.TipoBalanceDto
import com.miscuentas.errors.TipoBalanceErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.services.tipoBalances.TipoBalanceService
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
private const val ENDPOINT = "/tipoBalances"

fun Routing.tipoBalanceRoute() {

    val tipoBalanceService by inject<TipoBalanceService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todos los tipos de balance
            get({
                description = "Obtener todos los tipos de balance (Necesario Token)"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de tipos de balance."
                        body<List<TipoBalanceDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron tipos de balance."
                        body<String> {}
                    }
                }
            }) {
                tipoBalanceService.getAllTipoBalances().mapBoth(
                    success = { tipos -> call.respond(HttpStatusCode.OK, tipos.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.NotFound, handleTipoBalanceError(error)) }
                )
            }

            // Obtener tipo de balance por código
            get("/{codigo}", {
                description = "Obtener tipo de balance por código (Necesario Token)"
                request {
                    pathParameter<String>("codigo") {
                        description = "Código del tipo de balance."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Tipo de balance encontrado."
                        body<TipoBalanceDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró el tipo de balance."
                        body<String> {}
                    }
                }
            }) {
                val codigo = call.parameters["codigo"]
                if (codigo != null) {
                    tipoBalanceService.getTipoBalanceById(codigo).mapBoth(
                        success = { tipo -> call.respond(HttpStatusCode.OK, tipo.toDto()) },
                        failure = { error -> call.respond(HttpStatusCode.NotFound, handleTipoBalanceError(error)) }
                    )
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Código inválido.")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleTipoBalanceError(error: Any) {
    when (error) {
        is TipoBalanceErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is TipoBalanceErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is TipoBalanceErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is TipoBalanceErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
