package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class TipoBalanceDto(
    val tipo: String,
    val descripcion: String
)

/** DTO para crear un nuevo TipoBalance **/
@Serializable
data class TipoBalanceCrearDto(
    val tipo: String,
    val descripcion: String
)