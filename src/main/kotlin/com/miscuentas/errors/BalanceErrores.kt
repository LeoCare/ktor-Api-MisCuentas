package com.miscuentas.errors

/**
 * Clase sellada que representa los diferentes tipos de errores relacionados con los Balances.
 *
 * @property message Mensaje descriptivo del error.
 */
sealed class BalanceErrores(val message: String) {
    class NotFound(message: String) : BalanceErrores(message)
    class BadRequest(message: String) : BalanceErrores(message)
    class InvalidAmount(message: String) : BalanceErrores(message)
    class Unauthorized(message: String) : BalanceErrores(message)
    class Forbidden(message: String) : BalanceErrores(message)
}
