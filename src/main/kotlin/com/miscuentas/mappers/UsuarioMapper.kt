package com.miscuentas.mappers

import com.miscuentas.dto.UsuarioCrearDto
import com.miscuentas.dto.UsuarioDto
import com.miscuentas.entities.UsuarioEntity
import com.miscuentas.models.TipoPerfil
import com.miscuentas.models.Usuario

fun Usuario.toDto() = UsuarioDto(
    id_usuario = this.id_usuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = this.perfil
)
fun List<Usuario>.toDto() = this.map { it.toDto() }

fun UsuarioEntity.toModel() = Usuario(
    id_usuario = this.id_usuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = this.perfil
)
fun List<UsuarioEntity>.toModel() = this.map { it.toModel() }

fun UsuarioCrearDto.toModel() = Usuario(
    id_usuario = this.id_usuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = this.perfil
)
