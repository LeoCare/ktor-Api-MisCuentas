package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un tipo de perfil general:
 * @property tipo código que identifica el tipo de perfil (por ejemplo, "ADMIN" para administradores, "USER" para usuarios regulares).
 * @property descripcion descripción del tipo de perfil.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoPerfilDto(
    val tipo: String,
    val descripcion: String
)

/** Serialización de un tipo de perfil nuevo a crear:
 * @property tipo código que identifica el tipo de perfil (por ejemplo, "ADMIN" para administradores, "USER" para usuarios regulares).
 * @property descripcion descripción del tipo de perfil.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoPerfilCrearDto(
    val tipo: String,
    val descripcion: String
)

/** Serialización de un tipo de perfil a actualizar:
 * @property tipo código que identifica el tipo de perfil (por ejemplo, "ADMIN" para administradores, "USER" para usuarios regulares).
 * @property descripcion nueva descripción del tipo de perfil.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoPerfilUpdateDto (
    val tipo: String,
    val descripcion: String
)
