package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un registro de email general:
 * @property idEmail id único para cada registro de email.
 * @property idParticipante id del participante asociado al email.
 * @property idPago id del pago asociado al email.
 * @property tipo tipo de email (por ejemplo, "envío" o "solicitud").
 * @property destinatario dirección de correo electrónico del destinatario.
 * @property asunto asunto del email.
 * @property contenido contenido del email.
 * @property fechaEnvio fecha y hora en que se envió el email.
 * @property estado estado del email (por ejemplo, "pendiente", "enviado", "fallido").
 * @constructor Instancia a serializar.
 * */
@Serializable
data class EmailLogDto(
    val idEmail: Long,
    val idParticipante: Long,
    val idPago: Long,
    val tipo: String,
    val destinatario: String,
    val asunto: String,
    val contenido: String,
    val fechaEnvio: String,
    val estado: String
)

/** Serialización de un registro de email nuevo a crear:
 * @property idParticipante id del participante asociado al email.
 * @property idPago id del pago asociado al email.
 * @property tipo tipo de email (por ejemplo, "envío" o "solicitud").
 * @property destinatario dirección de correo electrónico del destinatario.
 * @property asunto asunto del email.
 * @property contenido contenido del email.
 * @property estado estado del email (por ejemplo, "pendiente", "enviado", "fallido").
 * @constructor Instancia a serializar.
 * */
@Serializable
data class EmailLogCrearDto(
    val idParticipante: Long,
    val idPago: Long,
    val tipo: String,
    val destinatario: String,
    val asunto: String,
    val contenido: String,
    val estado: String
)

/** Serialización de un registro de email a actualizar:
 * @property idEmail id único del registro de email a actualizar.
 * @property estado nuevo estado del email (por ejemplo, "pendiente", "enviado", "fallido").
 * @constructor Instancia a serializar.
 * */
@Serializable
data class EmailLogUpdateDto (
    val idEmail: Long,
    val estado: String
)