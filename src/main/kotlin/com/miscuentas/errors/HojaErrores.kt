package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con las Hojas.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class HojaErrores(val message: String) {
    class NotFound(message: String) : HojaErrores(message)
    class BadRequest(message: String) : HojaErrores(message)
    class Unauthorized(message: String) : HojaErrores(message)
    class Forbidden(message: String) : HojaErrores(message)
    class InvalidStatus(message: String) : HojaErrores(message)
}
