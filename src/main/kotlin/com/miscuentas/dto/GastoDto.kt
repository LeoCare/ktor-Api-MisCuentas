package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class GastoDto(
    val idGasto: Long,
    val tipo: String,
    val concepto: String,
    val importe: String,
    val fechaGasto: String,
    val idParticipante: Long,
    val idImagen: Long
)

/** DTO para crear un nuevo Gasto **/
@Serializable
data class GastoCrearDto(
    val tipo: String,
    val concepto: String,
    val importe: String,
    val fechaGasto: String,
    val idParticipante: Long,
    val idImagen: Long
)