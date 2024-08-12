package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

object TipoPerfilesTable : Table("TIPO_PERFIL") {
    val tipo = varchar("tipo", 20)
    val descripcion = varchar("descripcion", 255)

    override val primaryKey = PrimaryKey(tipo, name = "PK_TipoPerfiles")
}

//TIPOS DE PERFILES
enum class TipoPerfil(val codigo: String) {
    ADMIN("A"),
    USUARIO("U");

    companion object {
        fun fromCodigo(codigo: String): TipoPerfil? {
            return entries.find { it.codigo == codigo }
        }
    }
}