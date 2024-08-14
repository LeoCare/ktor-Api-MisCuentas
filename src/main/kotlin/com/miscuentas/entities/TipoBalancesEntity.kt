package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

object TipoBalancesTable : Table("TIPO_BALANCE") {
    val tipo = varchar("tipo", 2)
    val descripcion = varchar("descripcion", 255)

    override val primaryKey = PrimaryKey(tipo, name = "PK_TipoBalance")
}

//TIPOS DE ESTADOS
enum class TipoBalance(val codigo: String) {
    DEUDOR("D"),
    ACREEDOR("A");

    companion object {
        fun fromCodigo(codigo: String): TipoBalance? {
            return entries.find { it.codigo == codigo }
        }
    }
}