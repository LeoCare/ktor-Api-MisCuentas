package com.miscuentas.mappers

import com.miscuentas.dto.BalanceCrearDto
import com.miscuentas.dto.BalanceDto
import com.miscuentas.entities.TipoBalance
import com.miscuentas.models.Balance

/**
 * Extensión para convertir una instancia de `Balance` a `BalanceDto`.
 *
 * @return Una instancia de `BalanceDto` con los datos del `Balance`.
 */
fun Balance.toDto() = BalanceDto(
    idBalance = this.idBalance,
    idHoja = this.idHoja,
    idParticipante = this.idParticipante,
    tipo = this.tipo.codigo,
    monto = this.monto.toString()
)

/**
 * Extensión para convertir una lista de `Balance` a una lista de `BalanceDto`.
 *
 * @return Una lista de instancias `BalanceDto` correspondientes a la lista de `Balance`.
 */
fun List<Balance>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `BalanceDto` a `Balance`.
 *
 * @return Una instancia de `Balance` con los datos del `BalanceDto`.
 */
fun BalanceDto.toModel() = Balance(
    idBalance = this.idBalance,
    idHoja = this.idHoja,
    idParticipante = this.idParticipante,
    tipo = TipoBalance.fromCodigo(this.tipo) ?: TipoBalance.DEUDOR,
    monto = this.monto.toBigDecimal()
)

/**
 * Extensión para convertir una instancia de `BalanceCrearDto` a `Balance`.
 *
 * @return Una instancia de `Balance` con los datos del `BalanceCrearDto`.
 */
fun BalanceCrearDto.toModel() = Balance(
    idBalance = 0,
    idHoja = this.idHoja,
    idParticipante = this.idParticipante,
    tipo = TipoBalance.fromCodigo(this.tipo) ?: TipoBalance.DEUDOR,
    monto = this.monto.toBigDecimal()
)

/**
 * Extensión para convertir una lista de `BalanceCrearDto` a una lista de `Balance`.
 *
 * @return Una lista de instancias `Balance` correspondientes a la lista de `BalanceCrearDto`.
 */
fun List<BalanceCrearDto>.toModel() = this.map { it.toModel() }
