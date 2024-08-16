package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con los Gastos.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class GastoErrores(val message: String) {
    class NotFound(message: String) : GastoErrores(message)
    class BadRequest(message: String) : GastoErrores(message)
    class InvalidAmount(message: String) : GastoErrores(message)
    class Unauthorized(message: String) : GastoErrores(message)
    class Forbidden(message: String) : GastoErrores(message)
}
