package com.miscuentas.entities

import com.miscuentas.models.TipoStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object HojasTable : Table("HOJAS") {
    val idHoja = long("id_hoja").autoIncrement()
    val titulo = varchar("titulo", 255)
    val fechaCreacion = date("fecha_creacion")
    val fechaCierre = date("fecha_cierre").nullable()
    val limiteGastos = decimal("limite_gastos", 10, 2)
    val status = varchar("status", 2) references TipoStatusTable.tipo
    val idUsuario = long("id_usuario") references UsuariosTable.id_usuario

    override val primaryKey = PrimaryKey(idHoja, name = "PK_Hoja_ID")
}