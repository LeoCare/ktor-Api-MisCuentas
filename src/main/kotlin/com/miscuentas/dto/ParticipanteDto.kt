package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParticipanteDto(
    val idParticipante: Long,
    val nombre: String,
    val correo: String,
    val idUsuario: Long,
    val idHoja: Long
)

/** DTO para crear un nuevo Participante **/
@Serializable
data class ParticipanteCrearDto(
    val nombre: String,
    val correo: String,
    val idUsuario: Long,
    val idHoja: Long
)