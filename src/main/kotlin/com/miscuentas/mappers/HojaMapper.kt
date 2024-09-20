package com.miscuentas.mappers

import com.miscuentas.dto.HojaCrearDto
import com.miscuentas.dto.HojaDto
import com.miscuentas.entities.TipoStatus
import com.miscuentas.models.Hoja
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

/**
 * Extensión para convertir una instancia de `Hoja` a `HojaDto`.
 *
 * @return Una instancia de `HojaDto` con los datos del `Hoja`.
 */
fun Hoja.toDto() = HojaDto(
    idHoja = this.idHoja,
    titulo = this.titulo,
    fechaCreacion = this.fechaCreacion.format(dateFormatter),
    fechaCierre = this.fechaCierre?.format(dateFormatter),
    limiteGastos = this.limiteGastos?.toString(),
    status = this.status.codigo,
    idUsuario = this.idUsuario
)

/**
 * Extensión para convertir una lista de `Hoja` a una lista de `HojaDto`.
 *
 * @return Una lista de instancias `HojaDto` correspondientes a la lista de `Hoja`.
 */
fun List<Hoja>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `HojaDto` a `Hoja`.
 *
 * @return Una instancia de `Hoja` con los datos del `HojaDto`.
 */
fun HojaDto.toModel() = Hoja(
    idHoja = this.idHoja,
    titulo = this.titulo,
    fechaCreacion = LocalDate.parse(this.fechaCreacion, dateFormatter),
    fechaCierre = this.fechaCierre?.let { LocalDate.parse(it, dateFormatter) },
    limiteGastos = this.limiteGastos?.toBigDecimal(),
    status = TipoStatus.fromCodigo(this.status) ?: TipoStatus.ABIERTO,
    idUsuario = this.idUsuario
)

/**
 * Extensión para convertir una instancia de `HojaCrearDto` a `Hoja`.
 *
 * @return Una instancia de `Hoja` con los datos del `HojaCrearDto`.
 */
fun HojaCrearDto.toModel() = Hoja(
    idHoja = 0,
    titulo = this.titulo,
    fechaCreacion = LocalDate.parse(this.fechaCreacion, dateFormatter),
    fechaCierre = this.fechaCierre?.let { LocalDate.parse(it, dateFormatter) },
    limiteGastos = this.limiteGastos?.toBigDecimal(),
    status = TipoStatus.fromCodigo(this.status) ?: TipoStatus.ABIERTO,
    idUsuario = this.idUsuario
)

/**
 * Extensión para convertir una lista de `HojaCrearDto` a una lista de `Hoja`.
 *
 * @return Una lista de instancias `Hoja` correspondientes a la lista de `HojaCrearDto`.
 */
fun List<HojaCrearDto>.toModel() = this.map { it.toModel() }
