package com.miscuentas.models

/** MODELO DE CLASE USUARIO:
 * @property id_usuario id unico para cada usuario.
 * @property nombre nombre del registro y usado para logearse.
 * @property correo unico para cada usuario.
 * @property contrasenna se almacenará cifrada.
 * @param perfil restringe el acceso y las acciones..
 * @constructor Instancia un usuario unico.
 */
data class Usuario(
    val id_usuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String?
)


