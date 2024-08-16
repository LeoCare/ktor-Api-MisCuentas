package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con los usuarios.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class UsuarioErrores(val message: String) {
    /**
     * Representa un error cuando un usuario no se encuentra.
     *
     * @param message Mensaje descriptivo del error.
     */
    class NotFound(message: String) : UsuarioErrores(message)
    /**
     * Representa un error de solicitud incorrecta relacionada con el usuario.
     */
    class BadRequest(message: String) : UsuarioErrores(message)

    /**
     * Representa un error de credenciales incorrectas cuando un usuario intenta autenticarse.
     */
    class BadCredentials(message: String) : UsuarioErrores(message)

    /**
     * Representa un error cuando el usuario tiene un rol no permitido para cierta operación.
     */
    class BadRole(message: String) : UsuarioErrores(message)

    /**
     * Representa un error de falta de autorización, cuando el usuario no tiene acceso a un recurso o acción.
     */
    class Unauthorized(message: String) : UsuarioErrores(message)

    /**
     * Representa un error de acceso denegado, cuando el usuario intenta realizar una acción prohibida.
     */
    class Forbidden(message: String) : UsuarioErrores(message)
}