package com.miscuentas.models

import com.miscuentas.entities.UsuariosTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Hojas : Table("HOJAS") {
    val id = long("id_hoja").autoIncrement()
    val titulo = varchar("titulo", 255)
    val fechaCreacion = date("fecha_creacion")
    val fechaCierre = date("fecha_cierre").nullable()
    val limiteGastos = decimal("limite_gastos", 10, 2)
    val status = varchar("status", 2) references TipoStatus.tipo
    val idUsuario = long("id_usuario") references UsuariosTable.id_usuario
}