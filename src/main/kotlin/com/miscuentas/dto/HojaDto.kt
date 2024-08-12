package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class HojaDto(
    val idHoja: Long,
    val titulo: String,
    val fechaCreacion: String, // Formato ISO para las fechas
    val fechaCierre: String?, // Puede ser nulo
    val limiteGastos: String, // Usa String para BigDecimal para serialización
    val status: String,
    val idUsuario: Long
)

/** DTO para crear una nueva Hoja **/
@Serializable
data class HojaCrearDto(
    val titulo: String,
    val fechaCreacion: String,
    val fechaCierre: String?,
    val limiteGastos: String,
    val status: String,
    val idUsuario: Long
)