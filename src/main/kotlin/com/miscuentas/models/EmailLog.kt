package com.miscuentas.models

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * MODELO DE CLASE EMAIL LOG:
 * @property idEmail id único para cada registro de correo electrónico.
 * @property idBalance id del balance.
 * @property tipo tipo de correo (por ejemplo, 'envio' o 'solicitud').
 * @property fechaEnvio fecha y hora en que se envió el correo.
 * @property status estado del email (por ejemplo, 'E' o 'S').
 * @constructor Instancia un registro de correo electrónico único.
 */
data class EmailLog(
    val idEmail: Long,
    val idBalance: Long,
    val tipo: String,
    val fechaEnvio: LocalDate?,
    val status: String
)