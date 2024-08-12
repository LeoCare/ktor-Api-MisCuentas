package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object EmailsTable : Table("EMAILS") {
    val idEmail = long("id_email").autoIncrement()
    val idParticipante = long("id_participante") references ParticipantesTable.idParticipante
    val idPago = long("id_pago") references PagosTable.idPago
    val tipo = varchar("tipo", 50)
    val destinatario = varchar("destinatario", 255)
    val asunto = varchar("asunto", 255)
    val contenido = text("contenido")
    val fechaEnvio = timestamp("fecha_envio").defaultExpression(CurrentTimestamp())
    val estado = varchar("estado", 50)

    override val primaryKey = PrimaryKey(idEmail, name = "PK_Email_ID")
}