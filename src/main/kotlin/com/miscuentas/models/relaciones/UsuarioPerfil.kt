package com.miscuentas.models.relaciones

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioPerfil(
    val nombre: String,
    val descripcion: String,
)