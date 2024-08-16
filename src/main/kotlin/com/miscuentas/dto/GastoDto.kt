package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un gasto general:
 * @property idGasto id único para cada gasto.
 * @property tipo tipo de gasto (por ejemplo, "alimentación", "transporte").
 * @property concepto descripción o concepto del gasto.
 * @property importe monto del gasto.
 * @property fechaGasto fecha en la que se realizó el gasto.
 * @property idParticipante id del participante asociado al gasto.
 * @property idImagen id de la imagen asociada al gasto (si aplica).
 * @constructor Instancia a serializar.
 * */
@Serializable
data class GastoDto(
    val idGasto: Long,
    val tipo: String,
    val concepto: String,
    val importe: String,
    val fechaGasto: String,
    val idParticipante: Long,
    val idImagen: Long
)

/** Serialización de un gasto nuevo a crear:
 * @property tipo tipo de gasto (por ejemplo, "alimentación", "transporte").
 * @property concepto descripción o concepto del gasto.
 * @property importe monto del gasto.
 * @property fechaGasto fecha en la que se realizó el gasto.
 * @property idParticipante id del participante asociado al gasto.
 * @property idImagen id de la imagen asociada al gasto (si aplica).
 * @constructor Instancia a serializar.
 * */
@Serializable
data class GastoCrearDto(
    val tipo: String,
    val concepto: String,
    val importe: String,
    val fechaGasto: String,
    val idParticipante: Long,
    val idImagen: Long
)

/** Serialización de un gasto a actualizar:
 * @property idGasto id único del gasto a actualizar.
 * @property tipo tipo de gasto (por ejemplo, "alimentación", "transporte").
 * @property concepto descripción o concepto del gasto.
 * @property importe monto del gasto.
 * @property fechaGasto fecha en la que se realizó el gasto.
 * @property idParticipante id del participante asociado al gasto.
 * @property idImagen id de la imagen asociada al gasto (si aplica).
 * @constructor Instancia a serializar.
 * */
@Serializable
data class GastoUpdateDto (
    val idGasto: Long,
    val tipo: String,
    val concepto: String,
    val importe: String,
    val fechaGasto: String,
    val idParticipante: Long,
    val idImagen: Long?
)