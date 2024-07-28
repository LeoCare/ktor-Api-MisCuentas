package com.miscuentas.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table


@Serializable
data class Usuario(
    val id_usuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String
)


