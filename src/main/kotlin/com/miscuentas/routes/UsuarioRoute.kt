package com.miscuentas.routes


import com.miscuentas.mappers.toResponse
import com.miscuentas.services.usuarios.UsuarioService
import com.miscuentas.models.Usuario
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun Routing.usuarioRoute(usuarioService: UsuarioService) {
    route("/usuario") {

        // OBTENCION DE USUARIO:
        get {
            val column = call.request.queryParameters["c"] //Con parametros pasado en la query
            val query = call.request.queryParameters["q"]
            if (!column.isNullOrEmpty() && !query.isNullOrEmpty()) {
                val users = usuarioService.getUsuariosBy(column, query)
                call.respond(HttpStatusCode.OK,users)
            }
            else {
                val usuarios = usuarioService.getAllUsuarios()
                call.respond(HttpStatusCode.OK, usuarios.toResponse() )
            }
        }

        get ("{id}"){ //Con parametro pasado en la url
            val id = call.parameters["id"]?.toLongOrNull()
            id?.let {
                usuarioService.getUsuarioById(it)?.let {user->
                    call.respond(HttpStatusCode.OK,user)
                } ?: call.respond(HttpStatusCode.NotFound,"No se ha encontrado ese usuario")
            } ?: call.respond(HttpStatusCode.BadGateway,"Indique un valor!!")
        }


        //AGREGAR USUARIO:
        post {
            val usuario = call.receive<Usuario>()
            try{
                val result = usuarioService.addUsuario(usuario)
                result?.let {
                    call.respond(HttpStatusCode.Created,it)
                } ?: call.respond(HttpStatusCode.NotImplemented,"Error agregando usuario!")
            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.BadRequest,e.message ?: "Excepcion de SQL al agregar usuario!!")
            }
        }

        //ACTUALIZACION USUARIO:
        put {
            try {
                val usuario = call.receive<Usuario>()
                val result = usuarioService.updateUsuario(usuario)
                if (result != null){
                    call.respond(HttpStatusCode.OK,"Actualizacion Exitosa!")
                }else{
                    call.respond(HttpStatusCode.NotImplemented,"No se ha podido actualizar el usuario!!")
                }
            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.BadRequest,e.message ?: "Excepcion de SQL al actualizar usuario!!")
            }
        }

        //ELIMINACION USUARIO:
        delete {
            val usuario = call.receive<Usuario>()
            val result = usuarioService.deleteUsuario(usuario)
            if (result){
                call.respond(HttpStatusCode.OK,"Eliminacion exitosa!!")
            }else{
                call.respond(HttpStatusCode.NotImplemented,"No se ha podido eliminar el usuario!!")
            }
        }

    }
}