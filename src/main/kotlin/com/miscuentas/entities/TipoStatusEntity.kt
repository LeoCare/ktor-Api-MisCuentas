package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

object TipoStatusTable : Table("TIPO_STATUS") {
    val tipo = varchar("tipo", 2)
    val descripcion = varchar("descripcion", 255)

    override val primaryKey = PrimaryKey(tipo, name = "PK_TipoStatus")
}