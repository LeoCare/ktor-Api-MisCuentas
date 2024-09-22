package com.miscuentas.routes

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.miscuentas.dto.*
import com.miscuentas.errors.UsuarioErrores
import com.miscuentas.mappers.toDto
import com.miscuentas.mappers.toModel
import com.miscuentas.services.auth.TokensService
import com.miscuentas.services.auth.getAuthenticatedUsuario
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
private const val ENDPOINT = "/usuarios"

//para ver la documentacion de la Api-rest -> http://192.168.7.3:8080/swagger
fun Routing.usuarioRoute() {

    val usuarioService by inject<UsuarioService>()
    val tokenService by inject<TokensService>()

    route("/$ENDPOINT") {

        // Obtencio de datos personales --> GET /api/usuarios/personal
        get("/verify", {
            description = "COMPRUEBA SI EL CORREO YA EXISTE."
            request {
                queryParameter<String>("correo") {
                    description = "correo a buscar"
                    required = true // Optional
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Retorna el usuario, si encuetra el correo"
                    body<UsuarioDto> { }
                }
                HttpStatusCode.NotFound to {
                    description = "Retorna mensaje de aviso, si no encuentra el correo."
                    body<String> { }
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
            }
        }) {
            logger.debug { "Get verify" }

            try {
                val correo = call.request.queryParameters["correo"]

                //si se pasa el correo:
                if (!correo.isNullOrEmpty()) {
                    // Lo busco en la bbdd:
                    usuarioService.checkCorreoExist(correo).mapBoth(
                        success = { usuario ->
                            call.respond(HttpStatusCode.OK,  usuario.toDto())
                        },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotImplemented, error.message)
                        }
                    )
                } else {
                    call.respond(HttpStatusCode.BadRequest, "No has especificado el correo!!")
                }
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al buscar el correo.")
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    e.message ?: "Error desconocido al buscar el correo."
                )
            }
        }

        // Registro de un usuario --> POST /api/usuarios/registro
        post("/registro", {
            description = "REGISTRO DE LOS NUEVOS USUARIOS"
            request {
                body<UsuarioCrearDto> {
                    description = "Instancia del usuario a crear."
                    required = true // Optional
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Retorna el usuario que se acaba de crear y el token."
                    body<UsuarioWithTokenDto> {}
                }
                HttpStatusCode.BadRequest to {
                    description = "Retorna mensaje de error si falta algun dato importante o excepcion de SQL."
                    body<String> {}
                }
                HttpStatusCode.NotImplemented to {
                    description = "Retorna mensaje de error en el servicio."
                    body<String> {}
                }
                HttpStatusCode.InternalServerError to {
                    description = "Retorna mensaje de error desconocido."
                    body<String> {}
                }
            }
        }) {
            logger.debug { "POST registro" }

            try {
                // Recoge el usuario y lo valida antes de convertirlo al modelo:
                val usuarioDto = call.receive<UsuarioCrearDto>()
                if (usuarioDto.nombre.isBlank() || usuarioDto.correo.isBlank() || usuarioDto.contrasenna.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Nombre, correo y contraseña son obligatorios.")
                    return@post
                }
                // Convierte:
                val usuario = usuarioDto.toModel()

                // Agrego usuario:
                usuarioService.addUsuario(usuario).mapBoth(
                    success = { usuarioCreado ->
                        val accessToken  = tokenService.generateAccessToken(usuarioCreado)
                        val refreshToken = tokenService.generateRefreshToken(usuarioCreado)
                        call.respond(HttpStatusCode.Created, UsuarioWithTokenDto(usuarioCreado.toDto(), accessToken, refreshToken))
                    },
                    failure = { error ->
                        call.respond(HttpStatusCode.BadRequest, handleUserError(error))
                    }
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL crear el usuario.")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el usuario.")
            }
        }


        // Login de un usuario --> POST /api/usuarios/login
        post("/login", {
            description = "LOGEO DE LOS USUARIOS YA REGISTRADOS."
            request {
                body<UsuarioLoginDto> {
                    description = "Instancia de un usuario con correo y contraseña."
                    required = true // Optional
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Retorna el usuario, con contraseña, junto con el Token para futuras peticiones."
                    body<UsuarioWithTokenDto> {}
                }
                HttpStatusCode.NotFound to {
                    description = "Retorna mensaje de aviso, si no encuentra los datos."
                    body<String> { }
                }
                HttpStatusCode.BadRequest to {
                    description = "Retorna mensaje de error de SQL."
                    body<String> {}
                }
                HttpStatusCode.Unauthorized to {
                    description = "Retorna mensaje de aviso, si el logeo no es aceptado."
                    body<String> {}
                }
                HttpStatusCode.InternalServerError to {
                    description = "Retorna mensaje de error desconocido."
                    body<String> {}
                }
            }
        }) {
            logger.debug { "POST login" }

            try {
                // Recoge el usuario y lo valida:
                val usuario = call.receive<UsuarioLoginDto>()
                if (usuario.correo.isBlank() || usuario.contrasenna.isBlank()) {
                    call.respond(HttpStatusCode.NotFound, "Correo y contraseña son obligatorios.")
                    return@post
                }

                // Comprueba si el logeo es correcto:
                usuarioService.checkUserEmailAndPassword(usuario.correo, usuario.contrasenna).mapBoth(
                    success = { usuarioLogeado ->
                        val accessToken  = tokenService.generateAccessToken(usuarioLogeado)
                        val refreshToken = tokenService.generateRefreshToken(usuarioLogeado)
                        call.respond(HttpStatusCode.OK, UsuarioWithTokenDto(usuarioLogeado.toDto(), accessToken, refreshToken))
                    },
                    failure = { error ->
                        call.respond(HttpStatusCode.Unauthorized, error.message)
                    }
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL crear el usuario.")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el usuario.")
            }
        }

        // Login de un usuario --> POST /api/refreshToken/login
        post("/refreshToken", {
            description = "REFRESCO DEL TOKEN DE ACCESO."
            request {
                body<RefreshTokenRequest> {
                    description = "Token de refresco a verificar."
                    required = true // Optional
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Retorna un nuevo Token de Acceso y un nuevo Token de Refresco."
                    body<TokenResponse> {}
                }
                HttpStatusCode.NotFound to {
                    description = "Retorna mensaje de aviso, si no encuentra los datos."
                    body<String> { }
                }
                HttpStatusCode.BadRequest to {
                    description = "Retorna mensaje de error de SQL."
                    body<String> {}
                }
                HttpStatusCode.Unauthorized to {
                    description = "Retorna mensaje de aviso, si el logeo no es aceptado."
                    body<String> {}
                }
                HttpStatusCode.InternalServerError to {
                    description = "Retorna mensaje de error desconocido."
                    body<String> {}
                }
            }
        }) {
            logger.debug { "POST refreshToken" }

            try {
                // Recoge el token de actualizacion:
                val refreshTokenRequest = call.receive<RefreshTokenRequest>()
                val refreshToken = refreshTokenRequest.refreshToken

                // Verifica el token de actualizacion:
                val verifier = tokenService.verifyRefreshToken()
                val decodedJWT = verifier.verify(refreshToken)
                val userId = decodedJWT.getClaim("userId").asString().toLong()

                // Obtengo el usuario y genero unos nuevos token de acceso y refresco
                usuarioService.getUsuarioById(userId).mapBoth(
                    success = { usuarioLogeado ->
                        val newAccessToken  = tokenService.generateAccessToken(usuarioLogeado)
                        val newRefreshToken = tokenService.generateRefreshToken(usuarioLogeado)
                        call.respond(HttpStatusCode.OK, TokenResponse(newAccessToken, newRefreshToken))
                    },
                    failure = { error ->
                        call.respond(HttpStatusCode.Unauthorized, error.message)
                    }
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al solicitar nuevo token.")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al solicitar nuevo token.")
            }
        }

        // Rutas con autenticacion JWT necesaria:
        authenticate {

            // Obtencio de datos personales --> GET /api/usuarios/personal
            get("/personal", {
                description = "SOLICITAR LOS DATOS DE USUARIOS PERSONALES. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna mi usuario para poder verificar los datos personales."
                        body<UsuarioDto> { description = "Instancia del usuario personal." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> { }
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
                }
            }) {
                logger.debug { "Get personal" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    // Obtencion de datos personales:
                    usuarioService.getUsuarioById(usuarioSolicitud.idUsuario).mapBoth(
                        success = { usuario ->
                            call.respond(HttpStatusCode.OK, usuario.toDto())
                        },
                        failure = { error ->
                            call.respond(HttpStatusCode.NotImplemented, error.message)
                        }
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL crear el usuario.")
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        e.message ?: "Error desconocido al crear el usuario."
                    )
                }
            }

            // Obtencion de lista de usuarios --> GET /api/usuarios
            get({
                description = "SOLICITAR LISTA DE LOS USUARIOS REGISTRADOS. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna lista de los usuarios registrados."
                        body<List<UsuarioDto>> { description = "Lista de instancias de usuarios." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> { }
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
                logger.debug { "Get usuarios" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    // Comprobar si la peticion la realiza un Admin:
                    usuarioService.isAdmin(usuarioSolicitud.idUsuario).onSuccess { isAdmin ->
                        if (isAdmin) {

                            // Recoge todos los usuarios:
                            usuarioService.getAllUsuarios().mapBoth(
                                success = { listUsuarios ->
                                    call.respond(HttpStatusCode.OK, listUsuarios.toDto())
                                },
                                failure = { error ->
                                    call.respond(HttpStatusCode.NotImplemented, handleUserError(error))
                                }
                            )
                        } else {
                            call.respond(
                                HttpStatusCode.Forbidden,
                                "Acceso denegado: solo los administradores pueden ver la lista de usuarios"
                            )
                        }
                    }.onFailure { // No es admin:
                        call.respond(HttpStatusCode.InternalServerError, "Error al verificar el perfil del usuario")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al botener la lista de usuarios.")
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        e.message ?: "Error desconocido al botener la lista de usuarios."
                    )
                }
            }

            // Obtencion de un datos concretos de usuarios --> GET /api/usuarios/WhenData
            get("/WhenData", {
                description = "SOLICITAR UNOS DATOS EN CONCRETO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
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
                        description = "Retorna lista de usuarios que coincidan con ese valor."
                        body<List<UsuarioDto>> { }
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

                    // Comprobar si la peticion la realiza un Admin:
                    usuarioService.isAdmin(usuarioSolicitud.idUsuario).onSuccess { isAdmin ->
                        if (isAdmin) {

                            //Obtener datos que coincidan con..
                            val column = call.request.queryParameters["c"] //Con parametros pasado en la query
                            val query = call.request.queryParameters["q"]

                            //si especifica columna y dato:
                            if (!column.isNullOrEmpty() && !query.isNullOrEmpty()) {
                                usuarioService.getUsuariosBy(column, query).mapBoth(
                                    success = { usuariosCoincidentes ->
                                        call.respond(HttpStatusCode.OK, usuariosCoincidentes.toDto())
                                              },
                                    failure = { error ->
                                        call.respond(HttpStatusCode.NotImplemented, error.message)
                                    }
                                )
                            } else {
                                call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                            }
                        }else {
                            call.respond(HttpStatusCode.Forbidden, "Acceso denegado: solo los administradores pueden actualizar usuarios")
                        }
                    }.onFailure { // No es admin:
                        call.respond(HttpStatusCode.InternalServerError, "Error al verificar el perfil del usuario")
                    }
                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL crear el usuario.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el usuario.")
                }
            }

            // Obtencion de un usuario segun id --> GET /api/usuarios/{id}
            get ("{id}", {
                description = "SOLICITAR DATOS SEGUN ID PASADO POR PARAMETRO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    pathParameter<Long>("id") {
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
                        description = "Retorna mensaje de error de SQL."
                        body<String> {}
                    }
                    HttpStatusCode.NotImplemented to {
                        description = "Retorna mensaje de error si la peticion a la BBDD falló."
                        body<String> { }
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
            }) { //Con parametro pasado en la url
                logger.debug { "Get {id}" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@get

                    // Comprobar si la peticion la realiza un Admin:
                    usuarioService.isAdmin(usuarioSolicitud.idUsuario).onSuccess { isAdmin ->
                        if(isAdmin){

                            // Recoge id y obtiene usuario:
                            val id = call.parameters["id"]?.toLongOrNull()
                            if (id != null) {
                                usuarioService.getUsuarioById(id).mapBoth(
                                    success = { usuario ->
                                        call.respond(HttpStatusCode.OK, usuario.toDto())
                                              },
                                    failure = { error ->
                                        call.respond(HttpStatusCode.NotImplemented, error.message)
                                    }
                                )
                            }else{
                                call.respond(HttpStatusCode.BadRequest, "No has especificado el dato requerido!!")
                            }
                        }else {
                            call.respond(HttpStatusCode.Forbidden, "Acceso denegado: solo los administradores pueden actualizar usuarios")
                        }
                    }.onFailure { // No es admin:
                        call.respond(HttpStatusCode.InternalServerError, "Error al verificar el perfil del usuario")
                    }
                }catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL crear el usuario.")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al crear el usuario.")
                }
            }


            // Actualiza los datos de un usuario --> PUT  /api/usuarios
            put ({
                description = "SOLICITAR ACTUALIZACION DE UN USUARIO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    body<UsuarioDto> {
                        description = "Intancia de un usuario."
                        required = true // Optional
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna el usuario actualizado."
                        body<UsuarioDto> { }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> { }
                    }
                    HttpStatusCode.NotImplemented to {
                        description = "Retorna mensaje de error si la peticion a la BBDD falló."
                        body<String> { }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error SQL."
                        body<String> { }
                    }
                    HttpStatusCode.Unauthorized to {
                        description = "Usuario no autenticado."
                        body<String> { }
                    }
                    HttpStatusCode.Forbidden to {
                        description = "Acceso denegado por falta de permisos."
                        body<String> { }
                    }
                }
            }) {
                logger.debug { "Put usuario" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@put

                    // Comprobar si la peticion la realiza un Admin:
                    usuarioService.isAdmin(usuarioSolicitud.idUsuario).onSuccess {isAdmin ->
                        if (isAdmin) {

                            // Recoge y valida el usuario antes de convertirlo:
                            val usuarioDto = call.receive<UsuarioDto>()
                            if (usuarioDto.nombre.isBlank() || usuarioDto.correo.isBlank()) {
                                call.respond(HttpStatusCode.BadRequest, "Nombre o correo no pueden estar vacíos")
                                return@put
                            }
                            // Convierte:
                            val usuario = usuarioDto.contrasenna?.let { it1 -> usuarioDto.toModel(contrasennaExistente = it1) }

                            // Actualizar usuario:
                            if (usuario != null) {
                                usuarioService.updateUsuario(usuario).mapBoth(
                                    success = { usuarioActualizado ->
                                        call.respond(HttpStatusCode.OK, usuarioActualizado.toDto())
                                    },
                                    failure = { error ->
                                        call.respond(HttpStatusCode.NotImplemented, handleUserError(error))
                                    }
                                )
                            }
                        } else {
                            call.respond(HttpStatusCode.Forbidden, "Acceso denegado: solo los administradores pueden actualizar usuarios")
                        }
                    }.onFailure { // No es admin:
                        call.respond(HttpStatusCode.InternalServerError, "Error al verificar el perfil del usuario")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar usuario")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar usuario")
                }
            }


            //Eliminacion de un usuario --> PUT  /api/usuarios
            delete ({
                description = "SOLICITAR ELIMINACION DE UN USUARIO. (Necesario Token)"
                operationId = "Se realiza comprobacion del Token y perfil Admin."
                securitySchemeName = "JWT-Auth"
                request {
                    body<UsuarioDeleteDto> {
                        description = "Intancia de un usuario."
                        required = true // Optional
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Retorna aviso de eliminacion correcta."
                        body<String> {}
                    }
                    HttpStatusCode.NotFound to {
                        description = "Retorna mensaje de aviso, si no encuentra los datos."
                        body<String> {}
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Retorna mensaje de error de SQL."
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
                    HttpStatusCode.Forbidden to {
                        description = "Acceso denegado por falta de permisos."
                        body<String> {}
                    }
                }
            }) {
                logger.debug { "Delete usuario" }

                try {
                    // Recoge Id del token y lo valida:
                    val usuarioSolicitud = getAuthenticatedUsuario(usuarioService) ?: return@delete

                    // Comprobar si la peticion la realiza un Admin:
                    usuarioService.isAdmin(usuarioSolicitud.idUsuario).onSuccess { isAdmin ->
                        if (isAdmin){

                            // Recoge el usuario:
                            val usuarioDto = call.receive<UsuarioDeleteDto>()
                            if (usuarioDto.nombre.isBlank() || usuarioDto.correo.isBlank()) {
                                call.respond(HttpStatusCode.BadRequest, "Nombre o correo no pueden estar vacíos")
                                return@delete
                            }

                            // Obtencion del modelo de usuario:
                            usuarioService.getUsuariosBy("correo", usuarioDto.correo).mapBoth(
                                success = { usuarioCoincidente ->
                                    val usuario = usuarioCoincidente.first()

                                    // Eliminacion del usuario:
                                    usuarioService.deleteUsuario(usuario).mapBoth(
                                        success = {
                                            call.respond(HttpStatusCode.OK, "Se ha eliminado el usuario correctamente.")
                                        },
                                        failure = { error ->
                                            call.respond(HttpStatusCode.NotImplemented, handleUserError(error))
                                        }
                                    )
                                },
                                failure = { error ->
                                    call.respond(HttpStatusCode.NotImplemented, handleUserError(error))
                                }
                            )
                        }
                        else {
                            call.respond(HttpStatusCode.Forbidden, "Acceso denegado: solo los administradores pueden actualizar usuarios")
                        }
                    }.onFailure { // No es admin:
                        call.respond(HttpStatusCode.InternalServerError, "Error al verificar el perfil del usuario")
                    }
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Excepción de SQL al actualizar usuario")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error desconocido al actualizar usuario")
                }
            }
        }
    }
}

/** MANEJADOR DE ERRORES
 * Recibe diferentes tipo de errores y lo devuelve en la respuesta Http.
 */
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
        else -> call.respond(HttpStatusCode.InternalServerError, "Error desconocido")
    }
}