package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de un participante general:
 * @property idParticipante id único para cada participante.
 * @property nombre nombre del participante.
 * @property correo dirección de correo electrónico del participante.
 * @property idUsuario id del usuario asociado al participante (si aplica).
 * @property idHoja id de la hoja asociada al participante.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class ParticipanteDto(
    val idParticipante: Long,
    val nombre: String,
    val correo: String? = null,
    val idUsuario: Long? = null,
    val idHoja: Long
)

/** Serialización de un participante nuevo a crear:
 * @property nombre nombre del participante.
 * @property correo dirección de correo electrónico del participante.
 * @property idUsuario id del usuario asociado al participante (si aplica).
 * @property idHoja id de la hoja asociada al participante.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class ParticipanteCrearDto(
    val nombre: String,
    val correo: String? = null,
    val idUsuario: Long? = null,
    val idHoja: Long
)

/** Serialización de un participante a actualizar:
 * @property idParticipante id único del participante a actualizar.
 * @property nombre nombre del participante.
 * @property correo dirección de correo electrónico del participante.
 * @property idUsuario id del usuario asociado al participante (si aplica).
 * @property idHoja id de la hoja asociada al participante.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class ParticipanteUpdateDto (
    val idParticipante: Long,
    val nombre: String,
    val correo: String,
    val idUsuario: Long?,
    val idHoja: Long
)
