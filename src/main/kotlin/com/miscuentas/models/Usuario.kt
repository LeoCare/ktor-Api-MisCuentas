package com.miscuentas.models


data class Usuario(
    val id_usuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String?
)


