package com.miscuentas.services.balances

import com.miscuentas.errors.BalanceErrores
import com.miscuentas.models.Balance
import com.github.michaelbull.result.*

interface BalanceService {
    suspend fun getBalanceById(idBalance: Long): Result<Balance, BalanceErrores>
    suspend fun getAllBalances(): Result<List<Balance>, BalanceErrores>
    suspend fun getBalancesBy(column: String, query: String): Result<List<Balance>, BalanceErrores>
    suspend fun addBalance(balance: Balance): Result<Balance, BalanceErrores>
    suspend fun updateBalance(balance: Balance): Result<Balance, BalanceErrores>
    suspend fun deleteBalance(balance: Balance): Result<Boolean, BalanceErrores>
    suspend fun saveAllBalances(balances: Iterable<Balance>): Result<List<Balance>, BalanceErrores>
}
