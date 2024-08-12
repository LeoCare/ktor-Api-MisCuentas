package com.miscuentas.entities

import com.miscuentas.models.Imagenes
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object GastosTable : Table("GASTOS") {
    val idGasto = long("id_gasto").autoIncrement()
    val tipo = varchar("tipo", 255)
    val concepto = text("concepto")
    val importe = decimal("importe", 10, 2)
    val fechaGasto = date("fecha_gasto")
    val idParticipante = long("id_participante") references ParticipantesTable.idParticipante
    val idImagen = long("id_imagen") references Imagenes.id

    override val primaryKey = PrimaryKey(idGasto, name = "PK_Gasto_ID")
}