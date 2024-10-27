package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un registro de email general:
 * @property idEmail id único para cada registro de correo electrónico.
 * @property idBalance id del balance.
 * @property tipo tipo de correo (por ejemplo, 'envio' o 'solicitud').
 * @property fechaEnvio fecha y hora en que se envió el correo.
 * @property status estado del email (por ejemplo, 'E' o 'S').
 * @constructor Instancia un registro de correo electrónico único.
 */
@Serializable
data class EmailLogDto(
    val idEmail: Long,
    val idBalance: Long,
    val tipo: String,
    val fechaEnvio: String? = null,
    val status: String
)

/** Serialización de un registro de email nuevo a crear:
 * @property idBalance id del balance.
 * @property tipo tipo de correo (por ejemplo, 'envio' o 'solicitud').
 * @property fechaEnvio fecha y hora en que se envió el correo.
 * @property status estado del email (por ejemplo, 'E' o 'S').
 * */
@Serializable
data class EmailLogCrearDto(
    val idBalance: Long,
    val tipo: String,
    val fechaEnvio: String? = null,
    val status: String
)

/** Serialización de un registro de email a actualizar:
 * @property idEmail id único del registro de email a actualizar.
 * @property status nuevo estado del email (por ejemplo, "pendiente", "enviado", "fallido").
 * @constructor Instancia a serializar.
 * */
@Serializable
data class EmailLogUpdateDto(
    val idEmail: Long,
    val status: String
)