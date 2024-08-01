package com.miscuentas.errors

sealed class UsuarioErrores(val message: String) {
    class NotFound(message: String) : UsuarioErrores(message)
    class BadRequest(message: String) : UsuarioErrores(message)
    class BadCredentials(message: String) : UsuarioErrores(message)
    class BadRole(message: String) : UsuarioErrores(message)
    class Unauthorized(message: String) : UsuarioErrores(message)
    class Forbidden(message: String) : UsuarioErrores(message)
}