package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serializacion de un usuario general:
 * @property idUsuario id unico para cada usuario.
 * @property nombre nombre del registro y usado para logearse.
 * @property correo unico para cada usuario.
 * @property contrasenna opcional para cambiar la contraseña.
 * @property perfil restringe el acceso y las acciones..
 * @constructor Instancia a serializar.
 * */
@Serializable
data class UsuarioDto (
    val idUsuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String?, // Campo opcional para cambiar la contraseña
    val perfil: String
)

/** Serializacion de un usuario nuevo a crear
 * @property nombre nombre del registro y usado para logearse.
 * @property correo unico para cada usuario.
 * @property contrasenna opcional para cambiar la contraseña.
 * @property perfil restringe el acceso y las acciones..
 * @constructor Instancia a serializar.
 * */
@Serializable
data class UsuarioCrearDto (
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String
)

/** Serializacion de un usuario a eliminar
 * @property nombre nombre del registro y usado para logearse.
 * @property correo unico para cada usuario.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class UsuarioDeleteDto (
    val nombre: String,
    val correo: String
)

/** Serializacion de un usuario para el logeo
 * @property correo unico para cada usuario.
 * @property contrasenna opcional para cambiar la contraseña.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class UsuarioLoginDto(
    val correo: String,
    val contrasenna: String
)

/** Serializacion de un usuario junto con el token
 * @property usuario instancia del UsuarioDto.
 * @property token token generado para ese usuario.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class UsuarioWithTokenDto(
    val usuario: UsuarioDto,
    val accessToken: String,
    val refreshToken: String
)

/** Serializacion de un usuario con la descripcion del perfil
 * @property nombre nombre del registro y usado para logearse.
 * @property descripcion descripcion del perfil.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class UsuarioPerfil(
    val nombre: String,
    val descripcion: String,
)

/** Serializacion del token a regrescar.
 * @property refreshToken token de autorizacion de refresco.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class RefreshTokenRequest(val refreshToken: String)

/** Serializacion de un nuevo token y token de refresco
 * @property accessToken token de acceso a solicitudes.
 * @property refreshToken toke de refresco tras vencimiento.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TokenResponse(val accessToken: String, val refreshToken: String)