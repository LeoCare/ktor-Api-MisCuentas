package com.miscuentas.entities

import com.miscuentas.models.TipoPerfil
import com.miscuentas.models.TipoPerfiles
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsuariosTable : Table("USUARIOS") {
    val id_usuario = long("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 255)
    val correo = varchar("correo", 255).uniqueIndex()
    val contrasenna = varchar("contrasenna", 255)
    val perfil = varchar("perfil", 20).references(TipoPerfiles.tipo, ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id_usuario, name = "PK_id_usuario") // el nombre es opcional

    //Obtener columna de la tabla para los metodos de busqueda.
    fun getColumnByName(columnName: String): Column<String>? {
        return when (columnName) {
            "nombre" -> nombre
            "correo" -> correo
            "contrasenna" -> contrasenna
            "perfil" -> perfil
            else -> null
        }
    }
}

data class UsuarioEntity(
    val id_usuario: Long,
    val nombre: String,
    val correo: String,
    val contrasenna: String,
    val perfil: String = TipoPerfil.Tipo.USER_M.name
)