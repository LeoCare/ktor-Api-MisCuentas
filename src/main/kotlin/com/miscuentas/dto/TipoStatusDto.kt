package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un tipo de estado general:
 * @property tipo código que identifica el tipo de estado (por ejemplo, "OPEN" para abierto, "CLOSED" para cerrado).
 * @property descripcion descripción del tipo de estado.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoStatusDto(
    val tipo: String,
    val descripcion: String
)

/** Serialización de un tipo de estado nuevo a crear:
 * @property tipo código que identifica el tipo de estado (por ejemplo, "OPEN" para abierto, "CLOSED" para cerrado).
 * @property descripcion descripción del tipo de estado.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoStatusCrearDto(
    val tipo: String,
    val descripcion: String
)

/** Serialización de un tipo de estado a actualizar:
 * @property tipo código que identifica el tipo de estado (por ejemplo, "OPEN" para abierto, "CLOSED" para cerrado).
 * @property descripcion nueva descripción del tipo de estado.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class TipoStatusUpdateDto (
    val tipo: String,
    val descripcion: String
)
