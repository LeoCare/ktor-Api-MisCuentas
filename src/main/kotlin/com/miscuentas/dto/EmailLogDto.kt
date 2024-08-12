package com.miscuentas.dto

import kotlinx.serialization.Serializable

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

/** DTO para crear un nuevo EmailLog **/
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