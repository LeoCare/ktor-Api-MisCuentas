package com.miscuentas.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.math.BigDecimal
import java.time.LocalDate

/**
 * MODELO DE CLASE GASTO:
 * @property idGasto id único para cada gasto.
 * @property tipo tipo de gasto.
 * @property concepto descripción del gasto.
 * @property importe cantidad de dinero gastada.
 * @property fechaGasto fecha en la que se realizó el gasto.
 * @property idParticipante id del participante asociado al gasto.
 * @property idImagen id de la imagen asociada al gasto.
 * @constructor Instancia un gasto único.
 */
data class Gasto(
    val idGasto: Long,
    val tipo: String,
    val concepto: String,
    val importe: BigDecimal,
    val fechaGasto: LocalDate,
    val idParticipante: Long,
    val idImagen: Long?
)