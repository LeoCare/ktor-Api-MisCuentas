package com.miscuentas.entities


import com.miscuentas.models.TipoBalance
import org.jetbrains.exposed.sql.Table

object BalancesTable : Table("BALANCES") {
    val idBalance = long("id_balance").autoIncrement()
    val idHoja = long("id_hoja") references HojasTable.idHoja
    val idParticipante = long("id_participante") references ParticipantesTable.idParticipante
    val tipo = varchar("tipo", 2) references TipoBalancesTable.tipo
    val monto = decimal("monto", 10, 2)

    override val primaryKey = PrimaryKey(idBalance, name = "PK_Balance_ID")
}