package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con las Imagenes.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class ImagenErrores(val message: String) {
    class NotFound(message: String) : ImagenErrores(message)
    class BadRequest(message: String) : ImagenErrores(message)
    class InvalidFormat(message: String) : ImagenErrores(message)
    class Unauthorized(message: String) : ImagenErrores(message)
    class Forbidden(message: String) : ImagenErrores(message)
}
