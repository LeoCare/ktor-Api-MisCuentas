package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con los Emails.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class EmailLogErrores(val message: String) {
    class NotFound(message: String) : EmailLogErrores(message)
    class BadRequest(message: String) : EmailLogErrores(message)
    class EmailSendFailed(message: String) : EmailLogErrores(message)
    class Unauthorized(message: String) : EmailLogErrores(message)
    class Forbidden(message: String) : EmailLogErrores(message)
}
