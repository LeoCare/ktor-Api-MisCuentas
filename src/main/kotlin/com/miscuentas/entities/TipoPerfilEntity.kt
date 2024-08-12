package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

object TipoPerfilTable : Table("TIPO_PERFIL") {
    val tipo = varchar("tipo", 20)
    val descripcion = varchar("descripcion", 255)

    override val primaryKey = PrimaryKey(tipo, name = "PK_TipoPerfiles")
}