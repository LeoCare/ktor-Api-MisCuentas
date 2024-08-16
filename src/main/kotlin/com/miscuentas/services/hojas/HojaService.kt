package com.miscuentas.services.hojas

import com.github.michaelbull.result.*
import com.miscuentas.errors.HojaErrores
import com.miscuentas.models.Hoja

interface HojaService {
    suspend fun getHojaById(idHoja: Long): Result<Hoja, HojaErrores>
    suspend fun getAllHojas(): Result<List<Hoja>, HojaErrores>
    suspend fun getHojasBy(column: String, query: String): Result<List<Hoja>, HojaErrores>
    suspend fun addHoja(hoja: Hoja): Result<Hoja, HojaErrores>
    suspend fun updateHoja(hoja: Hoja): Result<Hoja, HojaErrores>
    suspend fun deleteHoja(hoja: Hoja): Result<Boolean, HojaErrores>
    suspend fun saveAllHojas(hojas: Iterable<Hoja>): Result<List<Hoja>, HojaErrores>
}
