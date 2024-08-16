package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un tipo de balance general:
 * @property tipo código que identifica el tipo de balance (por ejemplo, "D" para deudor, "A" para acreedor).
 * @property descripcion descripción del tipo de balance.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoBalanceDto(
    val tipo: String,
    val descripcion: String
)

/** Serialización de un tipo de balance nuevo a crear:
 * @property tipo código que identifica el tipo de balance (por ejemplo, "D" para deudor, "A" para acreedor).
 * @property descripcion descripción del tipo de balance.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoBalanceCrearDto(
    val tipo: String,
    val descripcion: String
)

/** Serialización de un tipo de balance a actualizar:
 * @property tipo código que identifica el tipo de balance (por ejemplo, "D" para deudor, "A" para acreedor).
 * @property descripcion nueva descripción del tipo de balance.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoBalanceUpdateDto (
    val tipo: String,
    val descripcion: String
)
