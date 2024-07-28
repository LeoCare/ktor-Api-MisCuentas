package com.miscuentas.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Gastos : Table("GASTOS") {
    val id = long("id_gasto").autoIncrement()
    val tipo = varchar("tipo", 255)
    val concepto = text("concepto")
    val importe = decimal("importe", 10, 2)
    val fechaGasto = date("fecha_gasto")
    val idParticipante = long("id_participante") references Participantes.id
    val idImagen = long("id_imagen") references Imagenes.id
}