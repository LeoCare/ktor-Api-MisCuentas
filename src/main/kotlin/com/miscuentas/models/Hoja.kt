package com.miscuentas.models

import java.math.BigDecimal
import java.time.LocalDate

/**
 * MODELO DE CLASE HOJA:
 * @property idHoja id único para cada hoja.
 * @property titulo título de la hoja.
 * @property fechaCreacion fecha en que la hoja fue creada.
 * @property fechaCierre fecha en que la hoja fue cerrada, puede ser nula.
 * @property limiteGastos límite de gastos permitido en la hoja, puede ser nulo.
 * @property status estado de la hoja.
 * @property idUsuario referencia al id del usuario propietario de la hoja.
 * @constructor Instancia una hoja única.
 */
data class Hoja(
    val idHoja: Long,
    val titulo: String,
    val fechaCreacion: LocalDate,
    val fechaCierre: LocalDate?,
    val limiteGastos: BigDecimal,
    val status: String,
    val idUsuario: Long
)