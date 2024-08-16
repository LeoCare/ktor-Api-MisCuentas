package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con `TipoStatus`.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class TipoStatusErrores(val message: String) {
    class NotFound(message: String) : TipoStatusErrores(message)
    class BadRequest(message: String) : TipoStatusErrores(message)
    class AlreadyExists(message: String) : TipoStatusErrores(message)
}
