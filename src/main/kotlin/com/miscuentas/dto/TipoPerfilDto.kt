package com.miscuentas.dto

import kotlinx.serialization.Serializable


@Serializable
data class TipoPerfilDto(
    val tipo: String,
    val descripcion: String
)

/** DTO para crear un nuevo TipoPerfil **/
@Serializable
data class TipoPerfilCrearDto(
    val tipo: String,
    val descripcion: String
)