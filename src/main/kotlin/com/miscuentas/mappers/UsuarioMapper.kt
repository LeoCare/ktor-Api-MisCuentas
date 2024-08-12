package com.miscuentas.mappers

import com.miscuentas.dto.UsuarioCrearDto
import com.miscuentas.dto.UsuarioDto
import com.miscuentas.entities.TipoPerfil
import com.miscuentas.entities.TipoPerfilesTable
import com.miscuentas.models.Usuario

fun Usuario.toDto() = UsuarioDto(
    idUsuario = this.idUsuario,
    nombre = this.nombre,
    correo = this.correo,
    perfil = this.perfil.codigo
)
fun List<Usuario>.toDto() = this.map { it.toDto() }

fun UsuarioDto.toModel(contrasennaExistente: String) = Usuario(
    idUsuario = this.idUsuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.nuevaContrasenna ?: contrasennaExistente, // Debes proporcionar la contraseña cifrada
    perfil = TipoPerfil.fromCodigo(this.perfil) ?: TipoPerfil.USUARIO
)

fun UsuarioCrearDto.toModel() = Usuario(
    idUsuario = 0,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = TipoPerfil.fromCodigo(this.perfil) ?: TipoPerfil.USUARIO
)
fun List<UsuarioCrearDto>.toModel() = this.map { it.toModel() }