package com.miscuentas.routes

import com.miscuentas.dto.UsuarioCrearDto
import com.miscuentas.dto.UsuarioLoginDto
import com.miscuentas.dto.UsuarioWithTokenDto
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.usuarios.UsuarioService
import com.miscuentas.models.Usuario
import com.miscuentas.services.auth.TokensService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging
import org.jetbrains.exposed.exceptions.ExposedSQLException

private val logger = KotlinLogging.logger {}

fun Routing.usuarioRoute(
    usuarioService: UsuarioService,
    tokenService: TokensService
) {
    route("/usuario") {

        // Register a new user --> POST /api/users/register
        post("/registro") {
            logger.debug { "POST registro" }

            val usuario = call.receive<UsuarioCrearDto>().toModel()
            val result = usuarioService.addUsuario(usuario)
            result?.let {
                call.respond(HttpStatusCode.Created, it.toDto())
            } ?: call.respond(HttpStatusCode.NotImplemented, "Error registrando usuario!")
        }

        // Login a user --> POST /api/users/login
        post("/login") {
            logger.debug { "POST login" }

            val usuario = call.receive<UsuarioLoginDto>()
            val result = usuarioService.checkUserNameAndPassword(usuario.username, usuario.password)
            if (result != null) {
                val token = tokenService.generateJWT(result)
                call.respond(HttpStatusCode.OK, UsuarioWithTokenDto(result.toDto(), token))
            }
        }

        // Rutas con autenticacion JWT necesaria:
        authenticate {
            // OBTENCION DE USUARIO:
            get {
                try {
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    usuarioService.getUsuarioById(userId)?.let {
                        val column = call.request.queryParameters["c"] //Con parametros pasado en la query
                        val query = call.request.queryParameters["q"]
                        if (!column.isNullOrEmpty() && !query.isNullOrEmpty()) { //si especifica columna y dato..
                            val users = usuarioService.getUsuariosBy(column, query)
                            call.respond(HttpStatusCode.OK,users.toDto())
                        }
                        else { //si no, los busca todos
                            val usuarios = usuarioService.getAllUsuarios()
                            call.respond(HttpStatusCode.OK, usuarios.toDto() )
                        }
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

                    usuarioService.getUsuarioById(userId)?.let {
                        val id = call.parameters["id"]?.toLongOrNull()
                        id?.let {
                            usuarioService.getUsuarioById(it)?.let {user->
                                call.respond(HttpStatusCode.OK,user.toDto())
                            } ?: call.respond(HttpStatusCode.NotFound,"No se ha encontrado ese usuario")
                        } ?: call.respond(HttpStatusCode.BadGateway,"Indique un valor!!")
                    }
                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al obtener el usuario!!")
                }
            }


            //AGREGAR USUARIO:
            post {
                try {
                    // El token viene con el usuario principal en el reclamo.
                    // Viene con comillas, por ello hay que reemplazarlas.
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    //Compruebo que he obtenido un id valido, buscandolo en la BBDD..
                    usuarioService.getUsuarioById(userId)?.let{ //...si existe realizo la operacion:
                        val usuario = call.receive<Usuario>()

                        val result = usuarioService.addUsuario(usuario)
                        result?.let {
                            call.respond(HttpStatusCode.Created, it.toDto())
                        } ?: call.respond(HttpStatusCode.NotImplemented, "Error agregando usuario!")
                    }
                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al agregar usuario!!")
                }
            }

            //ACTUALIZACION USUARIO:
            put {
                try {
                    val userId = call.principal<JWTPrincipal>()
                        ?.payload?.getClaim("userId")
                        .toString().replace("\"", "").toLong()

                    usuarioService.getUsuarioById(userId)?.let {
                        val usuario = call.receive<Usuario>()
                        val result = usuarioService.updateUsuario(usuario)
                        if (result != null) {
                            call.respond(HttpStatusCode.OK, "Actualizacion Exitosa!")
                        } else {
                            call.respond(HttpStatusCode.NotImplemented, "No se ha podido actualizar el usuario!!")
                        }
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

                    usuarioService.getUsuarioById(userId)?.let {
                        val usuario = call.receive<Usuario>()
                        val result = usuarioService.deleteUsuario(usuario)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "Eliminacion exitosa!!")
                        } else {
                            call.respond(HttpStatusCode.NotImplemented, "No se ha podido eliminar el usuario!!")
                        }
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepcion de SQL al actualizar usuario!!")
                }
            }
        }
    }
}