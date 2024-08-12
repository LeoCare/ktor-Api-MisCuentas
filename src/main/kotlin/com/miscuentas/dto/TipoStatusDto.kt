package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class TipoStatusDto(
    val tipo: String,
    val descripcion: String
)

/** DTO para crear un nuevo TipoStatus **/
@Serializable
data class TipoStatusCrearDto(
    val tipo: String,
    val descripcion: String
)