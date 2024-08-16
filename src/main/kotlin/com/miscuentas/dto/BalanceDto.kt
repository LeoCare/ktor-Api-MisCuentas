package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un balance general:
 * @property idBalance id único para cada balance.
 * @property idHoja id de la hoja asociada al balance.
 * @property idParticipante id del participante asociado al balance.
 * @property tipo tipo de balance (por ejemplo, "deudor" o "acreedor").
 * @property monto monto del balance.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class BalanceDto(
    val idBalance: Long,
    val idHoja: Long,
    val idParticipante: Long,
    val tipo: String,
    val monto: String
)

/** Serialización de un balance nuevo a crear:
 * @property idHoja id de la hoja asociada al balance.
 * @property idParticipante id del participante asociado al balance.
 * @property tipo tipo de balance (por ejemplo, "deudor" o "acreedor").
 * @property monto monto del balance.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class BalanceCrearDto(
    val idHoja: Long,
    val idParticipante: Long,
    val tipo: String,
    val monto: String
)

/** Serialización de un balance a eliminar:
 * @property idBalance id único del balance a eliminar.
 * @property idHoja id de la hoja asociada al balance.
 * @property idParticipante id del participante asociado al balance.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class BalanceDeleteDto (
    val idBalance: Long,
    val idHoja: Long,
    val idParticipante: Long
)