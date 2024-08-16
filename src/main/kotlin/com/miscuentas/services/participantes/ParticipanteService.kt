package com.miscuentas.services.participantes

import com.github.michaelbull.result.*
import com.miscuentas.errors.ParticipanteErrores
import com.miscuentas.models.Participante

interface ParticipanteService {
    suspend fun getParticipanteById(idParticipante: Long): Result<Participante, ParticipanteErrores>
    suspend fun getAllParticipantes(): Result<List<Participante>, ParticipanteErrores>
    suspend fun getParticipantesBy(column: String, query: String): Result<List<Participante>, ParticipanteErrores>
    suspend fun addParticipante(participante: Participante): Result<Participante, ParticipanteErrores>
    suspend fun updateParticipante(participante: Participante): Result<Participante, ParticipanteErrores>
    suspend fun deleteParticipante(participante: Participante): Result<Boolean, ParticipanteErrores>
    suspend fun saveAllParticipantes(participantes: Iterable<Participante>): Result<List<Participante>, ParticipanteErrores>
}
