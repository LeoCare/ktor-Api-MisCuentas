package com.miscuentas.models

import java.math.BigDecimal
import java.time.LocalDate

/**
 * MODELO DE CLASE PAGO:
 * @property idPago id único para cada pago.
 * @property idParticipante id del participante pagador.
 * @property idBalance id del balance asociado al pago.
 * @property idBalancePagado id del balance pagado.
 * @property monto cantidad de dinero pagada.
 * @property idImagen id de la imagen asociada al pago.
 * @property fechaPago fecha en que se realizó el pago.
 * @property fechaConfirmacion fecha en que se confirmó el pago, puede ser nula.
 * @constructor Instancia un pago único.
 */
data class Pago(
    val idPago: Long,
    val idParticipante: Long,
    val idBalance: Long,
    val idBalancePagado: Long,
    val monto: BigDecimal,
    val idImagen: Long?,
    val fechaPago: LocalDate,
    val fechaConfirmacion: LocalDate?
)