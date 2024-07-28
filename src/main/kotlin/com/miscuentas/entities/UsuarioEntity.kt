package com.miscuentas.entities

import com.miscuentas.models.TipoPerfiles
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsuariosTable : Table("USUARIOS") {
    val id_usuario = long("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 255)
    val correo = varchar("correo", 255).uniqueIndex()
    val contrasenna = varchar("contrasenna", 255)
    val perfil = varchar("perfil", 2).references(TipoPerfiles.tipo, ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id_usuario, name = "PK_id_usuario") // el nombre es opcional
}

data class UsuarioEntity(
    val id_usuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String
)