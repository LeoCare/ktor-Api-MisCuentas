package com.miscuentas.models

import java.time.LocalDateTime

/**
 * MODELO DE CLASE EMAIL LOG:
 * @property idEmail id único para cada registro de correo electrónico.
 * @property idParticipante id del participante asociado al correo.
 * @property idPago id del pago asociado al correo.
 * @property tipo tipo de correo (por ejemplo, 'envio' o 'solicitud').
 * @property destinatario dirección de correo del destinatario.
 * @property asunto asunto del correo electrónico.
 * @property contenido cuerpo del mensaje de correo electrónico.
 * @property fechaEnvio fecha y hora en que se envió el correo.
 * @property estado estado del correo (por ejemplo, 'pendiente', 'enviado', 'fallido').
 * @constructor Instancia un registro de correo electrónico único.
 */
data class EmailLog(
    val idEmail: Long,
    val idParticipante: Long,
    val idPago: Long,
    val tipo: String,
    val destinatario: String,
    val asunto: String,
    val contenido: String,
    val fechaEnvio: LocalDateTime,
    val estado: String
)