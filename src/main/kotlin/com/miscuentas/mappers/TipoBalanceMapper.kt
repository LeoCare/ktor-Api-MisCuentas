package com.miscuentas.mappers

import com.miscuentas.dto.TipoBalanceDto
import com.miscuentas.models.TipoBalance

/**
 * Extensión para convertir una instancia de `TipoBalance` a `TipoBalanceDto`.
 *
 * @return Una instancia de `TipoBalanceDto` con los datos de `TipoBalance`.
 */
fun TipoBalance.toDto() = TipoBalanceDto(
    tipo = this.tipo,
    descripcion = this.tipo
)

/**
 * Extensión para convertir una lista de `TipoBalance` a una lista de `TipoBalanceDto`.
 *
 * @return Una lista de instancias `TipoBalanceDto` correspondientes a la lista de `TipoBalance`.
 */
fun List<TipoBalance>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `TipoBalanceDto` a `TipoBalance`.
 *
 * @return Una instancia de `TipoBalance` con los datos de `TipoBalanceDto`.
 */
fun TipoBalanceDto.toModel() = TipoBalance(
    tipo = this.tipo,
    descripcion = this.descripcion
)
