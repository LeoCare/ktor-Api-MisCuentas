package com.miscuentas.mappers

import com.miscuentas.dto.UsuarioCrearDto
import com.miscuentas.dto.UsuarioDto
import com.miscuentas.entities.TipoPerfil
import com.miscuentas.models.Usuario

/**
 * Extensión para convertir una instancia de `Usuario` a `UsuarioDto`.
 *
 * @return Una instancia de `UsuarioDto` con los datos del `Usuario`.
 */
fun Usuario.toDto() = UsuarioDto(
    idUsuario = this.idUsuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = this.perfil.codigo
)
/**
 * Extensión para convertir una lista de `Usuario` a una lista de `UsuarioDto`.
 *
 * @return Una lista de instancias `UsuarioDto` correspondientes a la lista de `Usuario`.
 */
fun List<Usuario>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `UsuarioDto` a `Usuario`.
 *
 * @param contrasennaExistente La contraseña cifrada existente, se usa si `contrasenna` en `UsuarioDto` es nula.
 * @return Una instancia de `Usuario` con los datos del `UsuarioDto`.
 */
fun UsuarioDto.toModel(contrasennaExistente: String) = Usuario(
    idUsuario = this.idUsuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna ?: contrasennaExistente, // Debes proporcionar la contraseña cifrada
    perfil = TipoPerfil.fromCodigo(this.perfil) ?: TipoPerfil.USER
)


/**
 * Extensión para convertir una instancia de `UsuarioCrearDto` a `Usuario`.
 *
 * @return Una instancia de `Usuario` con los datos del `UsuarioCrearDto`.
 */
fun UsuarioCrearDto.toModel() = Usuario(
    idUsuario = 0,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = TipoPerfil.fromCodigo(this.perfil) ?: TipoPerfil.USER
)

/**
 * Extensión para convertir una lista de `UsuarioCrearDto` a una lista de `Usuario`.
 *
 * @return Una lista de instancias `Usuario` correspondientes a la lista de `UsuarioCrearDto`.
 */
fun List<UsuarioCrearDto>.toModel() = this.map { it.toModel() }