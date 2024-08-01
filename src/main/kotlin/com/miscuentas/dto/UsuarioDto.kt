package com.miscuentas.dto

import com.miscuentas.models.TipoPerfil
import kotlinx.serialization.Serializable

/** Usuario a devolver en las respuestas **/
@Serializable
data class UsuarioDto (
    val id_usuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String?
)

/** Usuario nuevo a crear **/
@Serializable
data class UsuarioCrearDto (
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String? = TipoPerfil.Tipo.USER_M.name
)

/** Usuario para el logeo **/
@Serializable
data class UsuarioLoginDto(
    val username: String,
    val password: String
)

/** Usuario con token **/
@Serializable
data class UsuarioWithTokenDto(
    val user: UsuarioDto,
    val token: String
)