package com.miscuentas.models

import com.miscuentas.entities.TipoPerfil

/** MODELO DE CLASE USUARIO:
 * @property idUsuario id unico para cada usuario.
 * @property nombre nombre del registro y usado para logearse.
 * @property correo unico para cada usuario.
 * @property contrasenna se almacenará cifrada.
 * @property perfil restringe el acceso y las acciones..
 * @constructor Instancia un usuario unico.
 */
data class Usuario(
    val idUsuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: TipoPerfil = TipoPerfil.USER
)


