package com.miscuentas.services.balances

import com.github.michaelbull.result.*
import com.miscuentas.errors.BalanceErrores
import com.miscuentas.models.Balance
import com.miscuentas.repositories.balances.BalanceRepository
import mu.KotlinLogging
import java.math.BigDecimal

class BalanceServiceImp(
    private val balanceRepository: BalanceRepository
): BalanceService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllBalances(): Result<List<Balance>, BalanceErrores> {
        logger.debug { "Servicio: getAllBalances()" }

        return balanceRepository.getAll()?.let {
            logger.debug { "Servicio: balances encontrados en repositorio." }
            Ok(it)
        } ?: Err(BalanceErrores.NotFound("Balances no encontrados"))
    }

    override suspend fun getBalanceById(idBalance: Long): Result<Balance, BalanceErrores> {
        logger.debug { "Servicio: getBalanceById()" }

        return balanceRepository.getById(idBalance)?.let {
            logger.debug { "Servicio: balance encontrado en repositorio." }
            Ok(it)
        } ?: Err(BalanceErrores.NotFound("Balance no encontrado"))
    }

    override suspend fun getBalancesBy(column: String, query: String): Result<List<Balance>, BalanceErrores> {
        logger.debug { "Servicio: getBalancesBy()" }

        return balanceRepository.getAllBy(column, query)?.let { balances ->
            logger.debug { "Servicio: balances encontrados en repositorio." }
            Ok(balances)
        } ?: Err(BalanceErrores.NotFound("El balance: $query no se ha encontrado"))
    }

    override suspend fun updateBalance(balance: Balance): Result<Balance, BalanceErrores> {
        logger.debug { "Servicio: updateBalance()" }

        return balanceRepository.update(balance)?.let {
            logger.debug { "Servicio: balance actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(BalanceErrores.Forbidden("Balance no actualizado"))
    }

    override suspend fun deleteBalance(balance: Balance): Result<Boolean, BalanceErrores> {
        logger.debug { "Servicio: deleteBalance()" }

        return if (balanceRepository.delete(balance)) {
            logger.debug { "Servicio: Balance eliminado correctamente." }
            Ok(true)
        } else Err(BalanceErrores.NotFound("Balance no eliminado."))
    }

    override suspend fun addBalance(balance: Balance): Result<Balance, BalanceErrores> {
        logger.debug { "Servicio: addBalance()" }

        return balanceRepository.save(balance)?.let {
            logger.debug { "Servicio: balance guardado desde el repositorio." }
            Ok(it)
        } ?: Err(BalanceErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun saveAllBalances(balances: Iterable<Balance>): Result<List<Balance>, BalanceErrores> {
        logger.debug { "Servicio: saveAllBalances()" }

        return balanceRepository.saveAll(balances)?.let {
            Ok(it)
        } ?: Err(BalanceErrores.Forbidden("La inserción ha fallado."))
    }

    override suspend fun findBalancesByTipo(tipo: String): Result<List<Balance>, BalanceErrores> {
        logger.debug { "Servicio: findBalancesByTipo()" }

        return balanceRepository.findByTipo(tipo)?.let {
            Ok(it)
        } ?: Err(BalanceErrores.NotFound("Balances no encontrados para el tipo: $tipo"))
    }

    override suspend fun calculateTotalForHoja(idHoja: Long): Result<BigDecimal, BalanceErrores> {
        logger.debug { "Servicio: calculateTotalForHoja()" }

        return balanceRepository.calculateTotalForHoja(idHoja)?.let {
            Ok(it)
        } ?: Err(BalanceErrores.NotFound("No se encontró ningún balance para la hoja: $idHoja"))
    }
}
