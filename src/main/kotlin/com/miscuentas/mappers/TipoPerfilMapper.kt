package com.miscuentas.mappers

import com.miscuentas.dto.TipoPerfilDto
import com.miscuentas.models.TipoPerfil

/**
 * Extensión para convertir una instancia de `TipoPerfil` a `TipoPerfilDto`.
 *
 * @return Una instancia de `TipoPerfilDto` con los datos de `TipoPerfil`.
 */
fun TipoPerfil.toDto() = TipoPerfilDto(
    tipo = this.tipo,
    descripcion = this.descripcion
)

/**
 * Extensión para convertir una lista de `TipoPerfil` a una lista de `TipoPerfilDto`.
 *
 * @return Una lista de instancias `TipoPerfilDto` correspondientes a la lista de `TipoPerfil`.
 */
fun List<TipoPerfil>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `TipoPerfilDto` a `TipoPerfil`.
 *
 * @return Una instancia de `TipoPerfil` con los datos de `TipoPerfilDto`.
 */
fun TipoPerfilDto.toModel() = TipoPerfil(
    tipo = this.tipo,
    descripcion = this.descripcion
)


