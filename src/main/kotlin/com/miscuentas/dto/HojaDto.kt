package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de una hoja general:
 * @property idHoja id único para cada hoja.
 * @property titulo título de la hoja.
 * @property fechaCreacion fecha en la que se creó la hoja.
 * @property fechaCierre fecha en la que se cerró la hoja (si aplica).
 * @property limiteGastos límite de gastos para la hoja.
 * @property status estado de la hoja (por ejemplo, "abierta", "cerrada").
 * @property idUsuario id del usuario asociado a la hoja.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class HojaDto(
    val idHoja: Long,
    val titulo: String,
    val fechaCreacion: String, // Formato ISO para las fechas
    val fechaCierre: String?, // Puede ser nulo
    val limiteGastos: String, // Usa String para BigDecimal para serialización
    val status: String,
    val idUsuario: Long
)

/** Serialización de una hoja nueva a crear:
 * @property titulo título de la hoja.
 * @property fechaCreacion fecha en la que se creó la hoja.
 * @property fechaCierre fecha en la que se cerró la hoja (si aplica).
 * @property limiteGastos límite de gastos para la hoja.
 * @property status estado de la hoja (por ejemplo, "abierta", "cerrada").
 * @property idUsuario id del usuario asociado a la hoja.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class HojaCrearDto(
    val titulo: String,
    val fechaCreacion: String,
    val fechaCierre: String?,
    val limiteGastos: String,
    val status: String,
    val idUsuario: Long
)

/** Serialización de una hoja a actualizar:
 * @property idHoja id único de la hoja a actualizar.
 * @property titulo título de la hoja.
 * @property fechaCreacion fecha en la que se creó la hoja.
 * @property fechaCierre fecha en la que se cerró la hoja (si aplica).
 * @property limiteGastos límite de gastos para la hoja.
 * @property status estado de la hoja (por ejemplo, "abierta", "cerrada").
 * @property idUsuario id del usuario asociado a la hoja.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class HojaUpdateDto (
    val idHoja: Long,
    val titulo: String,
    val fechaCreacion: String,
    val fechaCierre: String?,
    val limiteGastos: String,
    val status: String,
    val idUsuario: Long
)