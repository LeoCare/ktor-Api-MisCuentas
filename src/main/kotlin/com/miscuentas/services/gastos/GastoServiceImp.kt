package com.miscuentas.services.gastos

import com.github.michaelbull.result.*
import com.miscuentas.errors.GastoErrores
import com.miscuentas.models.Gasto
import com.miscuentas.repositories.gastos.GastoRepository
import mu.KotlinLogging


class GastoServiceImp(
    private val gastoRepository: GastoRepository
): GastoService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllGastos(): Result<List<Gasto>, GastoErrores> {
        logger.debug { "Servicio: getAllGastos()" }

        return gastoRepository.getAll()?.let {
            logger.debug { "Servicio: gastos encontrados en el repositorio." }
            Ok(it)
        } ?: Err(GastoErrores.NotFound("Gastos no encontrados"))
    }

    override suspend fun getGastoById(idGasto: Long): Result<Gasto, GastoErrores> {
        logger.debug { "Servicio: getGastoById()" }

        return gastoRepository.getById(idGasto)?.let {
            logger.debug { "Servicio: gasto encontrado en el repositorio." }
            Ok(it)
        } ?: Err(GastoErrores.NotFound("Gasto no encontrado"))
    }

    override suspend fun getGastosBy(column: String, query: String): Result<List<Gasto>, GastoErrores> {
        logger.debug { "Servicio: getGastosBy()" }

        return gastoRepository.getAllBy(column, query)?.let { gastos ->
            logger.debug { "Servicio: gastos encontrados en el repositorio." }
            Ok(gastos)
        } ?: Err(GastoErrores.NotFound("El gasto: $query no se ha encontrado"))
    }

    override suspend fun updateGasto(gasto: Gasto): Result<Gasto, GastoErrores> {
        logger.debug { "Servicio: updateGasto()" }

        return gastoRepository.update(gasto)?.let {
            logger.debug { "Servicio: gasto actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(GastoErrores.Forbidden("Gasto no actualizado"))
    }

    override suspend fun deleteGasto(gasto: Gasto): Result<Boolean, GastoErrores> {
        logger.debug { "Servicio: deleteGasto()" }

        return if (gastoRepository.delete(gasto)) {
            logger.debug { "Servicio: Gasto eliminado correctamente." }
            Ok(true)
        } else Err(GastoErrores.NotFound("Gasto no eliminado."))
    }

    override suspend fun addGasto(gasto: Gasto): Result<Gasto, GastoErrores> {
        logger.debug { "Servicio: addGasto()" }

        return gastoRepository.save(gasto)?.let {
            logger.debug { "Servicio: gasto guardado desde el repositorio." }
            Ok(it)
        } ?: Err(GastoErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun saveAllGastos(gastos: Iterable<Gasto>): Result<List<Gasto>, GastoErrores> {
        logger.debug { "Servicio: saveAllGastos()" }

        return gastoRepository.saveAll(gastos)?.let {
            Ok(it)
        } ?: Err(GastoErrores.Forbidden("La inserción ha fallado."))
    }

    override suspend fun findGastosByTipo(tipo: String): Result<List<Gasto>, GastoErrores> {
        logger.debug { "Servicio: findGastosByTipo()" }

        return gastoRepository.findByTipo(tipo)?.let {
            Ok(it)
        } ?: Err(GastoErrores.NotFound("Gastos no encontrados para el tipo: $tipo"))
    }

    override suspend fun findGastosByParticipante(idParticipante: Long): Result<List<Gasto>, GastoErrores> {
        logger.debug { "Servicio: findGastosByParticipante()" }

        return gastoRepository.findByParticipante(idParticipante)?.let {
            Ok(it)
        } ?: Err(GastoErrores.NotFound("Gastos no encontrados para el participante: $idParticipante"))
    }
}
