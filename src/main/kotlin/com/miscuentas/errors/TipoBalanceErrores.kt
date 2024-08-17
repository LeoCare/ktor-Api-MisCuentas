package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con `TipoBalance`.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class TipoBalanceErrores(val message: String) {
    class NotFound(message: String) : TipoBalanceErrores(message)
    class BadRequest(message: String) : TipoBalanceErrores(message)
    class AlreadyExists(message: String) : TipoBalanceErrores(message)
    class Unauthorized(message: String) : TipoBalanceErrores(message)
    class Forbidden(message: String) : TipoBalanceErrores(message)
}
