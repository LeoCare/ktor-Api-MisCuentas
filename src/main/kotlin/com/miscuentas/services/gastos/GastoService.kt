package com.miscuentas.services.gastos

import com.miscuentas.errors.GastoErrores
import com.miscuentas.models.Gasto
import com.github.michaelbull.result.*

interface GastoService {
    suspend fun getGastoById(idGasto: Long): Result<Gasto, GastoErrores>
    suspend fun getAllGastos(): Result<List<Gasto>, GastoErrores>
    suspend fun getGastosBy(column: String, query: String): Result<List<Gasto>, GastoErrores>
    suspend fun addGasto(gasto: Gasto): Result<Gasto, GastoErrores>
    suspend fun updateGasto(gasto: Gasto): Result<Gasto, GastoErrores>
    suspend fun deleteGasto(gasto: Gasto): Result<Boolean, GastoErrores>
    suspend fun saveAllGastos(gastos: Iterable<Gasto>): Result<List<Gasto>, GastoErrores>
}
