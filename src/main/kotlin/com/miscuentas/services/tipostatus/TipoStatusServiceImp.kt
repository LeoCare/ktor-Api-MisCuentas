package com.miscuentas.services.tipostatus

import com.github.michaelbull.result.*
import com.miscuentas.errors.TipoStatusErrores
import com.miscuentas.models.TipoStatus
import com.miscuentas.repositories.tipostatus.TipoStatusRepository
import mu.KotlinLogging

class TipoStatusServiceImp(
    private val tipoStatusRepository: TipoStatusRepository
): TipoStatusService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllTipoStatus(): Result<List<TipoStatus>, TipoStatusErrores> {
        logger.debug { "Servicio: getAllTipoStatus()" }

        return tipoStatusRepository.getAll()?.let {
            logger.debug { "Servicio: tipos de status encontrados en repositorio." }
            Ok(it)
        } ?: Err(TipoStatusErrores.NotFound("Tipos de status no encontrados"))
    }

    override suspend fun getTipoStatusById(tipo: String): Result<TipoStatus, TipoStatusErrores> {
        logger.debug { "Servicio: getTipoStatusById()" }

        return tipoStatusRepository.getById(tipo)?.let {
            logger.debug { "Servicio: tipo de status encontrado en repositorio." }
            Ok(it)
        } ?: Err(TipoStatusErrores.NotFound("Tipo de status no encontrado"))
    }

    override suspend fun addTipoStatus(tipoStatus: TipoStatus): Result<TipoStatus, TipoStatusErrores> {
        logger.debug { "Servicio: addTipoStatus()" }

        return tipoStatusRepository.save(tipoStatus)?.let {
            logger.debug { "Servicio: tipo de status guardado desde el repositorio." }
            Ok(it)
        } ?: Err(TipoStatusErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun updateTipoStatus(tipoStatus: TipoStatus): Result<TipoStatus, TipoStatusErrores> {
        logger.debug { "Servicio: updateTipoStatus()" }

        return tipoStatusRepository.update(tipoStatus)?.let {
            logger.debug { "Servicio: tipo de status actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(TipoStatusErrores.Forbidden("Tipo de status no actualizado"))
    }

    override suspend fun deleteTipoStatus(tipoStatus: TipoStatus): Result<Boolean, TipoStatusErrores> {
        logger.debug { "Servicio: deleteTipoStatus()" }

        return if (tipoStatusRepository.delete(tipoStatus)) {
            logger.debug { "Servicio: Tipo de status eliminado correctamente." }
            Ok(true)
        } else Err(TipoStatusErrores.NotFound("Tipo de status no eliminado."))
    }
}
