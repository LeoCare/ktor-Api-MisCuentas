package com.miscuentas.mappers

import com.miscuentas.dto.GastoCrearDto
import com.miscuentas.dto.GastoDto
import com.miscuentas.models.Gasto
import java.time.LocalDate

/**
 * Extensión para convertir una instancia de `Gasto` a `GastoDto`.
 *
 * @return Una instancia de `GastoDto` con los datos del `Gasto`.
 */
fun Gasto.toDto() = GastoDto(
    idGasto = this.idGasto,
    tipo = this.tipo,
    concepto = this.concepto,
    importe = this.importe.toString(),
    fechaGasto = this.fechaGasto.toString(),
    idParticipante = this.idParticipante,
    idImagen = this.idImagen
)

/**
 * Extensión para convertir una lista de `Gasto` a una lista de `GastoDto`.
 *
 * @return Una lista de instancias `GastoDto` correspondientes a la lista de `Gasto`.
 */
fun List<Gasto>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `GastoDto` a `Gasto`.
 *
 * @return Una instancia de `Gasto` con los datos del `GastoDto`.
 */
fun GastoDto.toModel() = Gasto(
    idGasto = this.idGasto,
    tipo = this.tipo,
    concepto = this.concepto,
    importe = this.importe.toBigDecimal(),
    fechaGasto = LocalDate.parse(this.fechaGasto),
    idParticipante = this.idParticipante,
    idImagen = this.idImagen
)

/**
 * Extensión para convertir una instancia de `GastoCrearDto` a `Gasto`.
 *
 * @return Una instancia de `Gasto` con los datos del `GastoCrearDto`.
 */
fun GastoCrearDto.toModel() = Gasto(
    idGasto = 0,
    tipo = this.tipo,
    concepto = this.concepto,
    importe = this.importe.toBigDecimal(),
    fechaGasto = LocalDate.parse(this.fechaGasto),
    idParticipante = this.idParticipante,
    idImagen = this.idImagen
)

/**
 * Extensión para convertir una lista de `GastoCrearDto` a una lista de `Gasto`.
 *
 * @return Una lista de instancias `Gasto` correspondientes a la lista de `GastoCrearDto`.
 */
fun List<GastoCrearDto>.toModel() = this.map { it.toModel() }
