package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con los Pagos.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class PagoErrores(val message: String) {
    class NotFound(message: String) : PagoErrores(message)
    class BadRequest(message: String) : PagoErrores(message)
    class InvalidAmount(message: String) : PagoErrores(message)
    class Unauthorized(message: String) : PagoErrores(message)
    class Forbidden(message: String) : PagoErrores(message)
}

