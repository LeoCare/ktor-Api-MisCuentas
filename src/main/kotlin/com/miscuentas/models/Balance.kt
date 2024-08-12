package com.miscuentas.models

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

/**
 * MODELO DE CLASE BALANCE:
 * @property idBalance id único para cada balance.
 * @property idHoja id de la hoja asociada al balance.
 * @property idParticipante id del participante asociado al balance.
 * @property tipo tipo de balance (D o A, por ejemplo).
 * @property monto monto del balance.
 * @constructor Instancia un balance único.
 */
data class Balance(
    val idBalance: Long,
    val idHoja: Long,
    val idParticipante: Long,
    val tipo: String,
    val monto: BigDecimal
)
