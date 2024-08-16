package com.miscuentas.mappers

import com.miscuentas.dto.ParticipanteCrearDto
import com.miscuentas.dto.ParticipanteDto
import com.miscuentas.models.Participante

/**
 * Extensión para convertir una instancia de `Participante` a `ParticipanteDto`.
 *
 * @return Una instancia de `ParticipanteDto` con los datos del `Participante`.
 */
fun Participante.toDto() = ParticipanteDto(
    idParticipante = this.idParticipante,
    nombre = this.nombre,
    correo = this.correo,
    idUsuario = this.idUsuario,
    idHoja = this.idHoja
)

/**
 * Extensión para convertir una lista de `Participante` a una lista de `ParticipanteDto`.
 *
 * @return Una lista de instancias `ParticipanteDto` correspondientes a la lista de `Participante`.
 */
fun List<Participante>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `ParticipanteDto` a `Participante`.
 *
 * @return Una instancia de `Participante` con los datos del `ParticipanteDto`.
 */
fun ParticipanteDto.toModel() = Participante(
    idParticipante = this.idParticipante,
    nombre = this.nombre,
    correo = this.correo,
    idUsuario = this.idUsuario,
    idHoja = this.idHoja
)

/**
 * Extensión para convertir una instancia de `ParticipanteCrearDto` a `Participante`.
 *
 * @return Una instancia de `Participante` con los datos del `ParticipanteCrearDto`.
 */
fun ParticipanteCrearDto.toModel() = Participante(
    idParticipante = 0,
    nombre = this.nombre,
    correo = this.correo,
    idUsuario = this.idUsuario,
    idHoja = this.idHoja
)

/**
 * Extensión para convertir una lista de `ParticipanteCrearDto` a una lista de `Participante`.
 *
 * @return Una lista de instancias `Participante` correspondientes a la lista de `ParticipanteCrearDto`.
 */
fun List<ParticipanteCrearDto>.toModel() = this.map { it.toModel() }
