package com.miscuentas.repositories.balances

import com.miscuentas.models.Balance
import com.miscuentas.repositories.base.CrudRepository
import java.math.BigDecimal

/**
 * Repositorio para la entidad `Balance`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface BalanceRepository: CrudRepository<Balance, Long> {

    /**
     * Encuentra todos los balances por tipo (deudor o acreedor).
     *
     * @param tipo El tipo de balance a buscar.
     * @return Una lista de balances que coinciden con el tipo especificado, o `null` si no se encuentran.
     */
    suspend fun findByTipo(tipo: String): List<Balance>?

    /**
     * Calcula el total de los balances para una hoja específica.
     *
     * @param idHoja El ID de la hoja.
     * @return El total de los balances para la hoja especificada, o `null` si no se puede calcular.
     */
    suspend fun calculateTotalForHoja(idHoja: Long): BigDecimal?
}

