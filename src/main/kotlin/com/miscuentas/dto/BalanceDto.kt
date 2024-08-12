package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class BalanceDto(
    val idBalance: Long,
    val idHoja: Long,
    val idParticipante: Long,
    val tipo: String,
    val monto: String
)

/** DTO para crear un nuevo Balance **/
@Serializable
data class BalanceCrearDto(
    val idHoja: Long,
    val idParticipante: Long,
    val tipo: String,
    val monto: String
)