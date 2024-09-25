package com.miscuentas.mappers

import com.miscuentas.dto.PagoCrearDto
import com.miscuentas.dto.PagoDto
import com.miscuentas.models.Pago
import java.time.LocalDate

/**
 * Extensión para convertir una instancia de `Pago` a `PagoDto`.
 *
 * @return Una instancia de `PagoDto` con los datos del `Pago`.
 */
fun Pago.toDto() = PagoDto(
    idPago = this.idPago,
    idBalance = this.idBalance,
    idBalancePagado = this.idBalancePagado,
    monto = this.monto.toString(),
    idImagen = this.idImagen,
    fechaPago = this.fechaPago.format(dateFormatter),
    fechaConfirmacion = this.fechaConfirmacion?.format(dateFormatter)
)

/**
 * Extensión para convertir una lista de `Pago` a una lista de `PagoDto`.
 *
 * @return Una lista de instancias `PagoDto` correspondientes a la lista de `Pago`.
 */
fun List<Pago>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `PagoDto` a `Pago`.
 *
 * @return Una instancia de `Pago` con los datos del `PagoDto`.
 */
fun PagoDto.toModel() = Pago(
    idPago = this.idPago,
    idBalance = this.idBalance,
    idBalancePagado = this.idBalancePagado,
    monto = this.monto.toBigDecimal(),
    idImagen = this.idImagen,
    fechaPago = LocalDate.parse(this.fechaPago, dateFormatter),
    fechaConfirmacion = this.fechaConfirmacion?.let { LocalDate.parse(it, dateFormatter) }
)

/**
 * Extensión para convertir una instancia de `PagoCrearDto` a `Pago`.
 *
 * @return Una instancia de `Pago` con los datos del `PagoCrearDto`.
 */
fun PagoCrearDto.toModel() = Pago(
    idPago = 0,
    idBalance = this.idBalance,
    idBalancePagado = this.idBalancePagado,
    monto = this.monto.toBigDecimal(),
    idImagen = this.idImagen,
    fechaPago = LocalDate.parse(this.fechaPago, dateFormatter),
    fechaConfirmacion = this.fechaConfirmacion?.let { LocalDate.parse(it, dateFormatter) }
)

/**
 * Extensión para convertir una lista de `PagoCrearDto` a una lista de `Pago`.
 *
 * @return Una lista de instancias `Pago` correspondientes a la lista de `PagoCrearDto`.
 */
fun List<PagoCrearDto>.toModel() = this.map { it.toModel() }
