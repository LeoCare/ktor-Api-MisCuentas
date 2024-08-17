package com.miscuentas.entities

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

/**
 * Representa la tabla `EMAILS` en la base de datos.
 *
 * @property idEmail Columna de ID único para cada registro de email, con auto-incremento.
 * @property idParticipante Columna que almacena el ID del participante asociado al email. Es una clave foránea que referencia a la tabla `ParticipantesTable`.
 * @property idPago Columna que almacena el ID del pago asociado al email. Es una clave foránea que referencia a la tabla `PagosTable`.
 * @property tipo Columna que almacena el tipo de email (por ejemplo, "envío" o "solicitud").
 * @property destinatario Columna que almacena la dirección de correo electrónico del destinatario.
 * @property asunto Columna que almacena el asunto del email.
 * @property contenido Columna que almacena el contenido del email.
 * @property fechaEnvio Columna que almacena la fecha y hora en que se envió el email. Tiene una expresión por defecto que utiliza la fecha y hora actual.
 * @property estado Columna que almacena el estado del email (por ejemplo, "pendiente", "enviado", "fallido").
 * @property primaryKey Define la clave primaria de la tabla `EMAILS` usando la columna `idEmail`.
 */
object EmailLogTable : Table("EMAILS") {
    val idEmail = long("id_email").autoIncrement()
    val idParticipante = long("id_participante").references(ParticipantesTable.idParticipante, ReferenceOption.CASCADE)
    val idPago = long("id_pago").references(PagosTable.idPago, ReferenceOption.CASCADE)
    val tipo = varchar("tipo", 50)
    val destinatario = varchar("destinatario", 255)
    val asunto = varchar("asunto", 255)
    val contenido = text("contenido")
    val fechaEnvio = timestamp("fecha_envio").defaultExpression(CurrentTimestamp())
    val estado = varchar("estado", 50)

    /**
     * Define la clave primaria de la tabla `EMAILS` usando la columna `idEmail`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Email_ID`.
     */
    override val primaryKey = PrimaryKey(idEmail, name = "PK_Email_ID")
}