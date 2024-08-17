package com.miscuentas.services.participantes

import com.github.michaelbull.result.*
import com.miscuentas.errors.ParticipanteErrores
import com.miscuentas.models.Participante
import com.miscuentas.repositories.participantes.ParticipanteRepository
import mu.KotlinLogging
import java.math.BigDecimal

class ParticipanteServiceImp(
    private val participanteRepository: ParticipanteRepository
): ParticipanteService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllParticipantes(): Result<List<Participante>, ParticipanteErrores> {
        logger.debug { "Servicio: getAllParticipantes()" }

        return participanteRepository.getAll()?.let {
            logger.debug { "Servicio: participantes encontrados en el repositorio." }
            Ok(it)
        } ?: Err(ParticipanteErrores.NotFound("Participantes no encontrados"))
    }

    override suspend fun getParticipanteById(idParticipante: Long): Result<Participante, ParticipanteErrores> {
        logger.debug { "Servicio: getParticipanteById()" }

        return participanteRepository.getById(idParticipante)?.let {
            logger.debug { "Servicio: participante encontrado en el repositorio." }
            Ok(it)
        } ?: Err(ParticipanteErrores.NotFound("Participante no encontrado"))
    }

    override suspend fun getParticipantesBy(column: String, query: String): Result<List<Participante>, ParticipanteErrores> {
        logger.debug { "Servicio: getParticipantesBy()" }

        return participanteRepository.getAllBy(column, query)?.let { participantes ->
            logger.debug { "Servicio: participantes encontrados en el repositorio." }
            Ok(participantes)
        } ?: Err(ParticipanteErrores.NotFound("El participante: $query no se ha encontrado"))
    }

    override suspend fun updateParticipante(participante: Participante): Result<Participante, ParticipanteErrores> {
        logger.debug { "Servicio: updateParticipante()" }

        return participanteRepository.update(participante)?.let {
            logger.debug { "Servicio: participante actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(ParticipanteErrores.Forbidden("Participante no actualizado"))
    }

    override suspend fun deleteParticipante(participante: Participante): Result<Boolean, ParticipanteErrores> {
        logger.debug { "Servicio: deleteParticipante()" }

        return if (participanteRepository.delete(participante)) {
            logger.debug { "Servicio: Participante eliminado correctamente." }
            Ok(true)
        } else Err(ParticipanteErrores.NotFound("Participante no eliminado."))
    }

    override suspend fun addParticipante(participante: Participante): Result<Participante, ParticipanteErrores> {
        logger.debug { "Servicio: addParticipante()" }

        return participanteRepository.save(participante)?.let {
            logger.debug { "Servicio: participante guardado desde el repositorio." }
            Ok(it)
        } ?: Err(ParticipanteErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun saveAllParticipantes(participantes: Iterable<Participante>): Result<List<Participante>, ParticipanteErrores> {
        logger.debug { "Servicio: saveAllParticipantes()" }

        return participanteRepository.saveAll(participantes)?.let {
            Ok(it)
        } ?: Err(ParticipanteErrores.Forbidden("La inserción ha fallado."))
    }

    override suspend fun findParticipantesByHoja(idHoja: Long): Result<List<Participante>, ParticipanteErrores> {
        logger.debug { "Servicio: findParticipantesByHoja()" }

        return participanteRepository.findByHoja(idHoja)?.let {
            Ok(it)
        } ?: Err(ParticipanteErrores.NotFound("Participantes no encontrados para la hoja: $idHoja"))
    }

    override suspend fun findParticipanteByCorreo(correo: String): Result<Participante, ParticipanteErrores> {
        logger.debug { "Servicio: findParticipanteByCorreo()" }

        return participanteRepository.findByCorreo(correo)?.let {
            Ok(it)
        } ?: Err(ParticipanteErrores.NotFound("Participante no encontrado para el correo: $correo"))
    }
}
