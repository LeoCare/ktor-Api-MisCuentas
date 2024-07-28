package com.miscuentas.mappers

import com.miscuentas.entities.UsuarioEntity
import com.miscuentas.models.Usuario

fun Usuario.toResponse() = Usuario(
    id_usuario = this.id_usuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = this.perfil
)
fun List<Usuario>.toResponse() = this.map { it.toResponse() }

fun UsuarioEntity.toModel() = Usuario(
    id_usuario = this.id_usuario,
    nombre = this.nombre,
    correo = this.correo,
    contrasenna = this.contrasenna,
    perfil = this.perfil
)
fun List<UsuarioEntity>.toModel() = this.map { it.toModel() }