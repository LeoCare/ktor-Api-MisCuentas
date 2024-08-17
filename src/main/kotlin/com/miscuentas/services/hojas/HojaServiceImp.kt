package com.miscuentas.services.hojas

import com.github.michaelbull.result.*
import com.miscuentas.errors.HojaErrores
import com.miscuentas.models.Hoja
import com.miscuentas.repositories.hojas.HojaRepository
import mu.KotlinLogging

class HojaServiceImp(
    private val hojaRepository: HojaRepository
): HojaService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllHojas(): Result<List<Hoja>, HojaErrores> {
        logger.debug { "Servicio: getAllHojas()" }

        return hojaRepository.getAll()?.let {
            logger.debug { "Servicio: hojas encontradas en el repositorio." }
            Ok(it)
        } ?: Err(HojaErrores.NotFound("Hojas no encontradas"))
    }

    override suspend fun getHojaById(idHoja: Long): Result<Hoja, HojaErrores> {
        logger.debug { "Servicio: getHojaById()" }

        return hojaRepository.getById(idHoja)?.let {
            logger.debug { "Servicio: hoja encontrada en el repositorio." }
            Ok(it)
        } ?: Err(HojaErrores.NotFound("Hoja no encontrada"))
    }

    override suspend fun getHojasBy(column: String, query: String): Result<List<Hoja>, HojaErrores> {
        logger.debug { "Servicio: getHojasBy()" }

        return hojaRepository.getAllBy(column, query)?.let { hojas ->
            logger.debug { "Servicio: hojas encontradas en el repositorio." }
            Ok(hojas)
        } ?: Err(HojaErrores.NotFound("La hoja: $query no se ha encontrado"))
    }

    override suspend fun updateHoja(hoja: Hoja): Result<Hoja, HojaErrores> {
        logger.debug { "Servicio: updateHoja()" }

        return hojaRepository.update(hoja)?.let {
            logger.debug { "Servicio: hoja actualizada desde el repositorio." }
            Ok(it)
        } ?: Err(HojaErrores.Forbidden("Hoja no actualizada"))
    }

    override suspend fun deleteHoja(hoja: Hoja): Result<Boolean, HojaErrores> {
        logger.debug { "Servicio: deleteHoja()" }

        return if (hojaRepository.delete(hoja)) {
            logger.debug { "Servicio: Hoja eliminada correctamente." }
            Ok(true)
        } else Err(HojaErrores.NotFound("Hoja no eliminada."))
    }

    override suspend fun addHoja(hoja: Hoja): Result<Hoja, HojaErrores> {
        logger.debug { "Servicio: addHoja()" }

        return hojaRepository.save(hoja)?.let {
            logger.debug { "Servicio: hoja guardada desde el repositorio." }
            Ok(it)
        } ?: Err(HojaErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun saveAllHojas(hojas: Iterable<Hoja>): Result<List<Hoja>, HojaErrores> {
        logger.debug { "Servicio: saveAllHojas()" }

        return hojaRepository.saveAll(hojas)?.let {
            Ok(it)
        } ?: Err(HojaErrores.Forbidden("La inserción ha fallado."))
    }

    override suspend fun findHojasByStatus(status: String): Result<List<Hoja>, HojaErrores> {
        logger.debug { "Servicio: findHojasByStatus()" }

        return hojaRepository.findByStatus(status)?.let {
            Ok(it)
        } ?: Err(HojaErrores.NotFound("Hojas no encontradas para el estado: $status"))
    }

    override suspend fun findHojasByUsuario(idUsuario: Long): Result<List<Hoja>, HojaErrores> {
        logger.debug { "Servicio: findHojasByUsuario()" }

        return hojaRepository.findByUsuario(idUsuario)?.let {
            Ok(it)
        } ?: Err(HojaErrores.NotFound("Hojas no encontradas para el usuario: $idUsuario"))
    }
}
