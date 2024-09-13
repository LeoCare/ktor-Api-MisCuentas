package com.miscuentas.services.tipobalances

import com.github.michaelbull.result.*
import com.miscuentas.errors.TipoBalanceErrores
import com.miscuentas.models.TipoBalance
import com.miscuentas.repositories.tipobalances.TipoBalanceRepository
import mu.KotlinLogging

class TipoBalanceServiceImp(
    private val tipoBalanceRepository: TipoBalanceRepository
): TipoBalanceService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllTipoBalances(): Result<List<TipoBalance>, TipoBalanceErrores> {
        logger.debug { "Servicio: getAllTipoBalances()" }

        return tipoBalanceRepository.getAll()?.let {
            logger.debug { "Servicio: tipos de balance encontrados en repositorio." }
            Ok(it)
        } ?: Err(TipoBalanceErrores.NotFound("Tipos de balance no encontrados"))
    }

    override suspend fun getTipoBalanceById(tipo: String): Result<TipoBalance, TipoBalanceErrores> {
        logger.debug { "Servicio: getTipoBalanceById()" }

        return tipoBalanceRepository.getById(tipo)?.let {
            logger.debug { "Servicio: tipo de balance encontrado en repositorio." }
            Ok(it)
        } ?: Err(TipoBalanceErrores.NotFound("Tipo de balance no encontrado"))
    }

    override suspend fun addTipoBalance(tipoBalance: TipoBalance): Result<TipoBalance, TipoBalanceErrores> {
        logger.debug { "Servicio: addTipoBalance()" }

        return tipoBalanceRepository.save(tipoBalance)?.let {
            logger.debug { "Servicio: tipo de balance guardado desde el repositorio." }
            Ok(it)
        } ?: Err(TipoBalanceErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun updateTipoBalance(tipoBalance: TipoBalance): Result<TipoBalance, TipoBalanceErrores> {
        logger.debug { "Servicio: updateTipoBalance()" }

        return tipoBalanceRepository.update(tipoBalance)?.let {
            logger.debug { "Servicio: tipo de balance actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(TipoBalanceErrores.Forbidden("Tipo de balance no actualizado"))
    }

    override suspend fun deleteTipoBalance(tipoBalance: TipoBalance): Result<Boolean, TipoBalanceErrores> {
        logger.debug { "Servicio: deleteTipoBalance()" }

        return if (tipoBalanceRepository.delete(tipoBalance)) {
            logger.debug { "Servicio: Tipo de balance eliminado correctamente." }
            Ok(true)
        } else Err(TipoBalanceErrores.NotFound("Tipo de balance no eliminado."))
    }
}