package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class PagoDto(
    val idPago: Long,
    val idBalance: Long,
    val idBalancePagado: Long,
    val monto: String,
    val idImagen: Long,
    val fechaPago: String,
    val fechaConfirmacion: String?
)

/** DTO para crear un nuevo Pago **/
@Serializable
data class PagoCrearDto(
    val idBalance: Long,
    val idBalancePagado: Long,
    val monto: String,
    val idImagen: Long,
    val fechaPago: String,
    val fechaConfirmacion: String?
)