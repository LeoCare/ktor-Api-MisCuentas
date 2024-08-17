package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.GastoCrearDto
import com.miscuentas.dto.GastoDto
import com.miscuentas.dto.ImagenCrearDto
import com.miscuentas.dto.ImagenDto
import com.miscuentas.errors.GastoErrores
import com.miscuentas.errors.ImagenErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.gastos.GastoService
import com.miscuentas.services.imagenes.ImagenService
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
private const val ENDPOINT = "/imagenes"

fun Routing.imagenRoute() {

    val imagenService by inject<ImagenService>()

    route("/$ENDPOINT") {

        authenticate {

            // Obtener todas las imágenes
            get({
                description = "Obtener todas las imágenes (Necesario Token)"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de imágenes."
                        body<List<ImagenDto>> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontraron imágenes."
                        body<String> {}
                    }
                }
            }) {
                imagenService.getAllImagenes().mapBoth(
                    success = { imagenes -> call.respond(HttpStatusCode.OK, imagenes.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.NotFound, handleImagenError(error)) }
                )
            }

            // Obtener imagen por ID
            get("/{id}", {
                description = "Obtener imagen por ID (Necesario Token)"
                request {
                    pathParameter<Long>("id") {
                        description = "ID de la imagen."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Imagen encontrada."
                        body<ImagenDto> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se encontró la imagen."
                        body<String> {}
                    }
                }
            }) {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    imagenService.getImagenById(id).mapBoth(
                        success = { imagen -> call.respond(HttpStatusCode.OK, imagen.toDto()) },
                        failure = { error -> call.respond(HttpStatusCode.NotFound, handleImagenError(error)) }
                    )
                } else {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                }
            }

            // Crear nueva imagen
            post({
                description = "Crear nueva imagen (Necesario Token)"
                request {
                    body<ImagenCrearDto> {}
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Imagen creada."
                        body<ImagenDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al crear la imagen."
                        body<String> {}
                    }
                }
            }) {
                val imagenCrearDto = call.receive<ImagenCrearDto>()
                imagenService.addImagen(imagenCrearDto.toModel()).mapBoth(
                    success = { imagen -> call.respond(HttpStatusCode.Created, imagen.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.BadRequest, handleImagenError(error)) }
                )
            }

            // Actualizar una imagen
            put({
                description = "Actualizar una imagen (Necesario Token)"
                request {
                    body<ImagenDto> {}
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Imagen actualizada."
                        body<ImagenDto> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al actualizar la imagen."
                        body<String> {}
                    }
                }
            }) {
                val imagenDto = call.receive<ImagenDto>()
                imagenService.updateImagen(imagenDto.toModel()).mapBoth(
                    success = { imagen -> call.respond(HttpStatusCode.OK, imagen.toDto()) },
                    failure = { error -> call.respond(HttpStatusCode.BadRequest, handleImagenError(error)) }
                )
            }

            // Eliminar una imagen
            delete("/{id}", {
                description = "Eliminar una imagen por ID (Necesario Token)"
                request {
                    pathParameter<Long>("id") {
                        description = "ID de la imagen."
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Imagen eliminada."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error al eliminar la imagen."
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
                    // Recoge el id:
                    val id = call.parameters["id"]?.toLongOrNull()
                    if (id != null) {
                        // Obtengo la imagen:
                        imagenService.getImagenById(id).onSuccess { imagen ->
                            // Elimino la imagen:
                            imagenService.deleteImagen(imagen).mapBoth(
                                success = { call.respond(HttpStatusCode.OK, "Imagen eliminada correctamente.") },
                                failure = { error -> call.respond(HttpStatusCode.BadRequest, handleImagenError(error)) }
                            )
                        }.onFailure { error ->
                            call.respond(HttpStatusCode.NotImplemented, handleImagenError(error))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al eliminar la imagen")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al eliminar la imagen")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleImagenError(error: Any) {
    when (error) {
        is ImagenErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is ImagenErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is ImagenErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is ImagenErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}
