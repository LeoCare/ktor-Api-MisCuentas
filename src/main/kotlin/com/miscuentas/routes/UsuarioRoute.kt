package com.miscuentas.routes

import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onSuccess
import com.miscuentas.config.AppConfig
import com.miscuentas.dto.UsuarioCrearDto
import com.miscuentas.dto.UsuarioDto
import com.miscuentas.dto.UsuarioLoginDto
import com.miscuentas.dto.UsuarioWithTokenDto
import com.miscuentas.errors.UsuarioErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.usuarios.UsuarioService
import com.miscuentas.models.Usuario
import com.miscuentas.services.auth.TokensService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger {}

fun Routing.usuarioRoute() {

    val usuarioService by inject<UsuarioService>()
    val tokenService by inject<TokensService>()

    route("/usuario") {

        // Register a new user --> POST /api/users/register
        post("/registro") {
            logger.debug { "POST registro" }

            val usuario = call.receive<UsuarioCrearDto>().toModel()
            usuarioService.addUsuario(usuario).mapBoth(
                success = { call.respond(HttpStatusCode.Created, it.toDto()) },
                failure = { call.respond(HttpStatusCode.BadRequest, handleUserError(it)) }
            )
        }

        // Login a user --> POST /api/users/login
        post("/login") {
            logger.debug { "POST login" }

            val usuario = call.receive<UsuarioLoginDto>()
            usuarioService.checkUserNameAndPassword(usuario.username, usuario.password).mapBoth(
                success = {
                    val token = tokenService.generateJWT(it)
                    call.respond(HttpStatusCode.OK, UsuarioWithTokenDto(it.toDto(), token))
                },
                failure = { call.respond(HttpStatusCode.Unauthorized, it.message) }
            )
        }

        // Rutas con autenticacion JWT necesaria:
        authenticate {
            // OBTENCION DE USUARIO:
            get("/personal") {
                try {
                    // El token viene con el usuario principal en el reclamo.
                    // Viene con comillas, por ello hay que reemplazarlas.
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    usuarioService.isAdmin(userId).onSuccess {//...si soy admin y existe realizo la operacion:
                        usuarioService.getUsuarioById(userId).mapBoth(
                            success = { call.respond(HttpStatusCode.OK,it.toDto()) },
                            failure = { call.respond(HttpStatusCode.NotFound, it.message) }
                        )
                    }
                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al obtener usuario!!")
                }
            }

            get("/lista") {
                try {
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    usuarioService.isAdmin(userId).onSuccess {
                        usuarioService.getAllUsuarios().mapBoth(
                            success = { call.respond(HttpStatusCode.OK, it.toDto()) },
                            failure = { call.respond(HttpStatusCode.NotFound, handleUserError(it)) }
                        )
                    }

                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al obtener usuarios!!")
                }
            }

            get("/WhenData") {
                try {
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    usuarioService.isAdmin(userId).onSuccess {
                        //Obtener datos que coincidan con..
                        val column = call.request.queryParameters["c"] //Con parametros pasado en la query
                        val query = call.request.queryParameters["q"]
                        if (!column.isNullOrEmpty() && !query.isNullOrEmpty()) { //si especifica columna y dato..
                            usuarioService.getUsuariosBy(column, query).mapBoth(
                                success = { call.respond(HttpStatusCode.OK, it.toDto()) },
                                failure = { call.respond(HttpStatusCode.NotFound, it.message) }
                            )

                        } else call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                    }
                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al obtener usuario!!")
                }
            }

            get ("{id}"){ //Con parametro pasado en la url
                try {
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    val id = call.parameters["id"]?.toLongOrNull()
                    id?.let {
                        usuarioService.isAdmin(userId).onSuccess {
                            usuarioService.getUsuarioById(id).mapBoth(
                                success = { call.respond(HttpStatusCode.OK, it.toDto()) },
                                failure = { call.respond(HttpStatusCode.NotFound, it.message) }
                            )
                        }
                    }

                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al obtener el usuario!!")
                }
            }


            //ACTUALIZACION USUARIO:
            put {
                try {
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    usuarioService.isAdmin(userId).onSuccess {
                        val usuario = call.receive<UsuarioDto>().toModel()
                        usuarioService.updateUsuario(usuario).mapBoth(
                            success = { call.respond(HttpStatusCode.OK, it.toDto()) },
                            failure = { call.respond(HttpStatusCode.NotImplemented, handleUserError(it)) }
                        )
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al actualizar usuario!!")
                }
            }

            //ELIMINACION USUARIO:
            delete {
                try {
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    usuarioService.isAdmin(userId).onSuccess {
                        val usuario = call.receive<UsuarioDto>().toModel()
                        usuarioService.deleteUsuario(usuario).mapBoth(
                            success = { call.respond(HttpStatusCode.OK, "Se ha eliminado el usuario correctamente.") },
                            failure = { call.respond(HttpStatusCode.NotImplemented, handleUserError(it)) }
                        )
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al eliminar el usuario!!")
                }
            }
        }
    }
}

// Manejador de errores
private suspend fun PipelineContext<Unit, ApplicationCall>.handleUserError(
    error: Any
) {
    when (error) {
        // Users
        is UsuarioErrores.BadRequest -> call.respond(HttpStatusCode.BadRequest, error.message)
        is UsuarioErrores.NotFound -> call.respond(HttpStatusCode.NotFound, error.message)
        is UsuarioErrores.Unauthorized -> call.respond(HttpStatusCode.Unauthorized, error.message)
        is UsuarioErrores.Forbidden -> call.respond(HttpStatusCode.Forbidden, error.message)
        is UsuarioErrores.BadCredentials -> call.respond(HttpStatusCode.BadRequest, error.message)
        is UsuarioErrores.BadRole -> call.respond(HttpStatusCode.Forbidden, error.message)
    }
}