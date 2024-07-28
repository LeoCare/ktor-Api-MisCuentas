package com.miscuentas.models

import org.jetbrains.exposed.sql.Table

object Balances : Table("BALANCES") {
    val id = long("id_balance").autoIncrement()
    val idHoja = long("id_hoja") references Hojas.id
    val idParticipante = long("id_participante") references Participantes.id
    val tipo = varchar("tipo", 2) references TipoBalance.tipo
    val monto = decimal("monto", 10, 2)
}
