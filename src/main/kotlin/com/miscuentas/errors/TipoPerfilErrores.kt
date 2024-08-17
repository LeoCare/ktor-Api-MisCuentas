package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con `TipoPerfil`.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class TipoPerfilErrores(val message: String) {
    class NotFound(message: String) : TipoPerfilErrores(message)
    class BadRequest(message: String) : TipoPerfilErrores(message)
    class AlreadyExists(message: String) : TipoPerfilErrores(message)
    class Unauthorized(message: String) : TipoPerfilErrores(message)
    class Forbidden(message: String) : TipoPerfilErrores(message)
}
