package com.miscuentas.services.balances

import com.miscuentas.errors.BalanceErrores
import com.miscuentas.models.Balance
import com.github.michaelbull.result.*
import java.math.BigDecimal

/**
 * Interfaz que define los servicios relacionados con la entidad Balance.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los balances en la aplicación.
 */
interface BalanceService {

    /**
     * Obtiene todos los balances.
     *
     * @return Un `Result` que contiene una lista de balances o un error de tipo `BalanceErrores`.
     */
    suspend fun getAllBalances(): Result<List<Balance>, BalanceErrores>

    /**
     * Obtiene un balance por su ID.
     *
     * @param idBalance El ID del balance a buscar.
     * @return Un `Result` que contiene el balance encontrado o un error de tipo `BalanceErrores`.
     */
    suspend fun getBalanceById(idBalance: Long): Result<Balance, BalanceErrores>

    /**
     * Obtiene balances que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de balances encontrados o un error de tipo `BalanceErrores`.
     */
    suspend fun getBalancesBy(column: String, query: String): Result<List<Balance>, BalanceErrores>

    /**
     * Agrega un nuevo balance.
     *
     * @param balance La entidad de balance a agregar.
     * @return Un `Result` que contiene el balance agregado o un error de tipo `BalanceErrores`.
     */
    suspend fun addBalance(balance: Balance): Result<Balance, BalanceErrores>

    /**
     * Actualiza la información de un balance existente.
     *
     * @param balance La entidad de balance con los datos actualizados.
     * @return Un `Result` que contiene el balance actualizado o un error de tipo `BalanceErrores`.
     */
    suspend fun updateBalance(balance: Balance): Result<Balance, BalanceErrores>

    /**
     * Elimina un balance.
     *
     * @param balance La entidad de balance a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `BalanceErrores`.
     */
    suspend fun deleteBalance(balance: Balance): Result<Boolean, BalanceErrores>

    /**
     * Guarda una lista de balances en la base de datos.
     *
     * @param balances La lista de entidades de balance a guardar.
     * @return Un `Result` que contiene una lista de los balances guardados o un error de tipo `BalanceErrores`.
     */
    suspend fun saveAllBalances(balances: Iterable<Balance>): Result<List<Balance>, BalanceErrores>

    /**
     * Encuentra balances por tipo.
     *
     * @param tipo El tipo de balance a buscar.
     * @return Un `Result` que contiene una lista de balances encontrados o un error de tipo `BalanceErrores`.
     */
    suspend fun findBalancesByTipo(tipo: String): Result<List<Balance>, BalanceErrores>

    /**
     * Calcula el total de balances para una hoja específica.
     *
     * @param idHoja El ID de la hoja para la cual calcular el total de balances.
     * @return Un `Result` que contiene el total calculado o un error de tipo `BalanceErrores`.
     */
    suspend fun calculateTotalForHoja(idHoja: Long): Result<BigDecimal, BalanceErrores>
}

