package com.miscuentas.models

import org.jetbrains.exposed.sql.Table

object TipoBalance : Table("TIPO_BALANCE") {
    val tipo = varchar("tipo", 2)
    val descripcion = varchar("descripcion", 255)
}