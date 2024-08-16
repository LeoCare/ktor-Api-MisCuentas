package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con los Participantes.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class ParticipanteErrores(val message: String) {
    class NotFound(message: String) : ParticipanteErrores(message)
    class BadRequest(message: String) : ParticipanteErrores(message)
    class Unauthorized(message: String) : ParticipanteErrores(message)
    class Forbidden(message: String) : ParticipanteErrores(message)
    class DuplicateEntry(message: String) : ParticipanteErrores(message)
}
