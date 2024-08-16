package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

/**
 * Representa la tabla `BALANCES` en la base de datos.
 *
 * @property idBalance Columna de ID único para cada balance, con auto-incremento.
 * @property idHoja Columna que almacena el ID de la hoja asociada al balance. Es una clave foránea que referencia a la tabla `HojasTable`.
 * @property idParticipante Columna que almacena el ID del participante asociado al balance. Es una clave foránea que referencia a la tabla `ParticipantesTable`.
 * @property tipo Columna que almacena el tipo de balance (por ejemplo, "D" para deudor, "A" para acreedor). Es una clave foránea que referencia a la tabla `TipoBalancesTable`.
 * @property monto Columna que almacena el monto del balance.
 * @property primaryKey Define la clave primaria de la tabla `BALANCES` usando la columna `idBalance`.
 */
object BalancesTable : Table("BALANCES") {
    val idBalance = long("id_balance").autoIncrement()
    val idHoja = long("id_hoja") references HojasTable.idHoja
    val idParticipante = long("id_participante") references ParticipantesTable.idParticipante
    val tipo = varchar("tipo", 2) references TipoBalancesTable.tipo
    val monto = decimal("monto", 10, 2)

    /**
     * Define la clave primaria de la tabla `BALANCES` usando la columna `idBalance`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Balance_ID`.
     */
    override val primaryKey = PrimaryKey(idBalance, name = "PK_Balance_ID")
}