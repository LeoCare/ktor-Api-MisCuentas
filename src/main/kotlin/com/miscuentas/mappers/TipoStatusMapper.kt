package com.miscuentas.mappers

import com.miscuentas.dto.TipoStatusDto
import com.miscuentas.models.TipoStatus

/**
 * Extensión para convertir una instancia de `TipoStatus` a `TipoStatusDto`.
 *
 * @return Una instancia de `TipoStatusDto` con los datos de `TipoStatus`.
 */
fun TipoStatus.toDto() = TipoStatusDto(
    tipo = this.tipo,
    descripcion = this.descripcion
)

/**
 * Extensión para convertir una lista de `TipoStatus` a una lista de `TipoStatusDto`.
 *
 * @return Una lista de instancias `TipoStatusDto` correspondientes a la lista de `TipoStatus`.
 */
fun List<TipoStatus>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `TipoStatusDto` a `TipoStatus`.
 *
 * @return Una instancia de `TipoStatus` con los datos de `TipoStatusDto`.
 */
fun TipoStatusDto.toModel() = TipoStatus(
    tipo = this.tipo,
    descripcion = this.descripcion
)
