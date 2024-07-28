package com.miscuentas.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object EmailLog : Table("EMAIL_LOG") {
    val id = long("id_email").autoIncrement()
    val idParticipante = long("id_participante") references Participantes.id
    val idPago = long("id_pago") references Pagos.id
    val tipo = varchar("tipo", 50)
    val destinatario = varchar("destinatario", 255)
    val asunto = varchar("asunto", 255)
    val contenido = text("contenido")
    val fechaEnvio = timestamp("fecha_envio").defaultExpression(CurrentTimestamp())
    val estado = varchar("estado", 50)
}