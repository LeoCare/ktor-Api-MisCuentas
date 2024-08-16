package com.miscuentas.services.pagos

import com.github.michaelbull.result.*
import com.miscuentas.errors.PagoErrores
import com.miscuentas.models.Pago

interface PagoService {
    suspend fun getPagoById(idPago: Long): Result<Pago, PagoErrores>
    suspend fun getAllPagos(): Result<List<Pago>, PagoErrores>
    suspend fun getPagosBy(column: String, query: String): Result<List<Pago>, PagoErrores>
    suspend fun addPago(pago: Pago): Result<Pago, PagoErrores>
    suspend fun updatePago(pago: Pago): Result<Pago, PagoErrores>
    suspend fun deletePago(pago: Pago): Result<Boolean, PagoErrores>
    suspend fun saveAllPagos(pagos: Iterable<Pago>): Result<List<Pago>, PagoErrores>
}
