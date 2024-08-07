package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.UsuarioCrearDto
import com.miscuentas.dto.UsuarioDto
import com.miscuentas.dto.UsuarioLoginDto
import com.miscuentas.dto.UsuarioWithTokenDto
import com.miscuentas.errors.UsuarioErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.auth.TokensService
import com.miscuentas.services.usuarios.UsuarioService
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
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
private const val ENDPOINT = "/usuario"

//para ver la documentacion de la Api-rest -> http://192.168.7.3:8080/swagger
fun Routing.usuarioRoute() {

    val usuarioService by inject<UsuarioService>()
    val tokenService by inject<TokensService>()

    route("/$ENDPOINT") {

        // Register a new user --> POST /api/users/register
        post("/registro", {
            description = "REGISTRO DE LOS NUEVOS USUARIOS"
            request {
                queryParameter<UsuarioCrearDto>("Usuario") {
                    description = "Instancia del usuario a crear."
                    required = true // Optional
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Retorna el usuario que se acaba de crear."
                    body<UsuarioDto> {}
                }
                HttpStatusCode.BadRequest to {
                    description = "Retorna mensaje de error SQL."
                    body<String> {}
                }
                HttpStatusCode.NotImplemented to {
                    description = "Retorna mensaje de error en el servicio."
                    body<String> {}
                }
            }
        }) {
            logger.debug { "POST registro" }

            val usuario = call.receive<UsuarioCrearDto>().toModel()
            usuarioService.addUsuario(usuario).mapBoth(
                success = { call.respond(HttpStatusCode.Created, it.toDto()) },
                failure = { call.respond(HttpStatusCode.BadRequest, handleUserError(it)) }
            )
        }

        // Login a user --> POST /api/users/login
        post("/login", {
            description = "LOGEO DE LOS USUARIOS YA REGISTRADOS."
            request {
                queryParameter<UsuarioLoginDto>("Usuario y contraseña para el logeo") {
                    description = "Instancia de un usuario con nombre y contraseña."
                    required = true // Optional
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Retorna el usuario junto con el Token para futuras peticiones."
                    body<UsuarioWithTokenDto> {}
                }
                HttpStatusCode.Unauthorized to {
                    description = "Retorna mensaje de aviso, si el logeo no es aceptado."
                    body<String> {}
                }
            }
        }) {
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
            get("/personal", {
                description = "SOLICITAR LOS DATOS DE USUARIOS PERSONALES. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna mi usuario para poder verificar los datos personales."
                        body<UsuarioDto> { description = "Instancia del usuario personal." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> { }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
                        body<String> {}
                    }
                }
            }) {
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

            get("/lista", {
                description = "SOLICITAR LISTA DE LOS USUARIOS REGISTRADOS. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna lista de los usuarios registrados."
                        body<List<UsuarioDto>> { description = "Lista de instancias de usuarios." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> { }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
                        body<String> {}
                    }
                }
            }) {
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

            get("/WhenData", {
                description = "SOLICITAR UNOS DATOS EN CONCRETO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
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
                        description = "Retorna lista de usuarios que coincidan con ese valor."
                        body<List<UsuarioDto>> { }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL o si no se ha proporcionado los datos."
                        body<String> {}
                    }
                }
            }) {
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

            get ("{id}", {
                description = "SOLICITAR DATOS SEGUN ID PASADO POR PARAMETRO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                request {
                    pathParameter<Int>("id_usuario") {
                        description = "id de un usuario en concreto."
                        required = true // Optional
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna usuario segun el id pasado por parametro."
                        body<UsuarioDto> { }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> { }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL o si no se ha proporcionado los datos."
                        body<String> {}
                    }
                }
            }) { //Con parametro pasado en la url
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


            // ACTUALIZACION USUARIO: --> PUT /usuario
            put ({
                description = "SOLICITAR ACTUALIZACION DE UN USUARIO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                request {
                    queryParameter<UsuarioDto>("Intancia de un usuario") {
                        description = "Intancia de un usuario."
                        required = true // Optional
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna usuario ya actualizado."
                        body<UsuarioDto> { }
                    }
                    HttpStatusCode.NotImplemented to {
                        description = "Retorna mensaje de aviso, si no ha actualizado los datos."
                        body<String> {  }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
                        body<String> { }
                    }
                }
            }) {
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
            delete ({
                description = "SOLICITAR ELIMINACION DE UN USUARIO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                request {
                    queryParameter<UsuarioDto>("Intancia de un usuario") {
                        description = "Intancia de un usuario."
                        required = true // Optional
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna aviso de eliminacion correcta."
                        body<String> { }
                    }
                    HttpStatusCode.NotImplemented to {
                        description = "Retorna mensaje de aviso, si no se ha eliminado el usuario."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
                        body<String> { }
                    }
                }
            }) {
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