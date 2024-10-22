package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un pago general:
 * @property idPago id único para cada pago.
 * @property idParticipante id del participante pagador.
 * @property idBalance id del balance asociado al pago.
 * @property idBalancePagado id del balance que ha sido pagado (si aplica).
 * @property monto monto del pago.
 * @property idImagen id de la imagen asociada al pago (si aplica).
 * @property fechaPago fecha en la que se realizó el pago.
 * @property fechaConfirmacion fecha en la que se confirmó el pago (si aplica).
 * @constructor Instancia a serializar.
 * */
@Serializable
data class PagoDto(
    val idPago: Long,
    val idParticipante: Long,
    val idBalance: Long,
    val idBalancePagado: Long,
    val monto: String,
    val idImagen: Long? = null,
    val fechaPago: String,
    val fechaConfirmacion: String? = null
)

/** Serialización de un pago nuevo a crear:
 * @property idParticipante id del participante pagador.
 * @property idBalance id del balance asociado al pago.
 * @property idBalancePagado id del balance que ha sido pagado (si aplica).
 * @property monto monto del pago.
 * @property idImagen id de la imagen asociada al pago (si aplica).
 * @property fechaPago fecha en la que se realizó el pago.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class PagoCrearDto(
    val idParticipante: Long,
    val idBalance: Long,
    val idBalancePagado: Long,
    val monto: String,
    val idImagen: Long? = null,
    val fechaPago: String,
    val fechaConfirmacion: String? = null
)

/** Serialización de un pago a actualizar:
 * @property idPago id único del pago a actualizar.
 * @property idBalance id del balance asociado al pago.
 * @property idBalancePagado id del balance que ha sido pagado (si aplica).
 * @property monto monto del pago.
 * @property idImagen id de la imagen asociada al pago (si aplica).
 * @property fechaConfirmacion fecha en la que se confirmó el pago (si aplica).
 * @constructor Instancia a serializar.
 * */
@Serializable
data class PagoUpdateDto (
    val idPago: Long,
    val idBalance: Long,
    val idBalancePagado: Long?,
    val monto: String,
    val idImagen: Long?,
    val fechaConfirmacion: String?
)
