package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Usuario a devolver en las respuestas **/
@Serializable
data class UsuarioDto (
    val idUsuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String?, // Campo opcional para cambiar la contraseña
    val perfil: String
)

/** Usuario nuevo a crear **/
@Serializable
data class UsuarioCrearDto (
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String
)

/** Usuario a eliminar **/
@Serializable
data class UsuarioDeleteDto (
    val nombre: String,
    val correo: String
)

/** Usuario para el logeo **/
@Serializable
data class UsuarioLoginDto(
    val correo: String,
    val contrasenna: String
)

/** Usuario con token **/
@Serializable
data class UsuarioWithTokenDto(
    val usuario: UsuarioDto,
    val token: String
)

@Serializable
data class UsuarioPerfil(
    val nombre: String,
    val descripcion: String,
)