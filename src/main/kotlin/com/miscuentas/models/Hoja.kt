package com.miscuentas.models

import com.miscuentas.entities.TipoStatus
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
    val fechaCierre: LocalDate? = null,
    val limiteGastos: BigDecimal? = null,
    val status: TipoStatus = TipoStatus.ABIERTO,
    val idUsuario: Long
)