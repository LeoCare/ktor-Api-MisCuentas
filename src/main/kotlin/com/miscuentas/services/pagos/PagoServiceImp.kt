package com.miscuentas.services.pagos

import com.github.michaelbull.result.*
import com.miscuentas.errors.PagoErrores
import com.miscuentas.models.Pago
import com.miscuentas.repositories.pagos.PagoRepository
import mu.KotlinLogging
import java.math.BigDecimal

class PagoServiceImp(
    private val pagoRepository: PagoRepository
): PagoService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllPagos(): Result<List<Pago>, PagoErrores> {
        logger.debug { "Servicio: getAllPagos()" }

        return pagoRepository.getAll()?.let {
            logger.debug { "Servicio: pagos encontrados en el repositorio." }
            Ok(it)
        } ?: Err(PagoErrores.NotFound("Pagos no encontrados"))
    }

    override suspend fun getPagoById(idPago: Long): Result<Pago, PagoErrores> {
        logger.debug { "Servicio: getPagoById()" }

        return pagoRepository.getById(idPago)?.let {
            logger.debug { "Servicio: pago encontrado en el repositorio." }
            Ok(it)
        } ?: Err(PagoErrores.NotFound("Pago no encontrado"))
    }

    override suspend fun getPagosBy(column: String, query: String): Result<List<Pago>, PagoErrores> {
        logger.debug { "Servicio: getPagosBy()" }

        return pagoRepository.getAllBy(column, query)?.let { pagos ->
            logger.debug { "Servicio: pagos encontrados en el repositorio." }
            Ok(pagos)
        } ?: Err(PagoErrores.NotFound("El pago: $query no se ha encontrado"))
    }

    override suspend fun updatePago(pago: Pago): Result<Pago, PagoErrores> {
        logger.debug { "Servicio: updatePago()" }

        return pagoRepository.update(pago)?.let {
            logger.debug { "Servicio: pago actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(PagoErrores.Forbidden("Pago no actualizado"))
    }

    override suspend fun deletePago(pago: Pago): Result<Boolean, PagoErrores> {
        logger.debug { "Servicio: deletePago()" }

        return if (pagoRepository.delete(pago)) {
            logger.debug { "Servicio: Pago eliminado correctamente." }
            Ok(true)
        } else Err(PagoErrores.NotFound("Pago no eliminado."))
    }

    override suspend fun addPago(pago: Pago): Result<Pago, PagoErrores> {
        logger.debug { "Servicio: addPago()" }

        return pagoRepository.save(pago)?.let {
            logger.debug { "Servicio: pago guardado desde el repositorio." }
            Ok(it)
        } ?: Err(PagoErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun saveAllPagos(pagos: Iterable<Pago>): Result<List<Pago>, PagoErrores> {
        logger.debug { "Servicio: saveAllPagos()" }

        return pagoRepository.saveAll(pagos)?.let {
            Ok(it)
        } ?: Err(PagoErrores.Forbidden("La inserción ha fallado."))
    }

    override suspend fun findPagosByBalance(idBalance: Long): Result<List<Pago>, PagoErrores> {
        logger.debug { "Servicio: findPagosByBalance()" }

        return pagoRepository.findByBalance(idBalance)?.let {
            Ok(it)
        } ?: Err(PagoErrores.NotFound("Pagos no encontrados para el balance: $idBalance"))
    }

    override suspend fun findPagosByFechaPago(fechaPago: String): Result<List<Pago>, PagoErrores> {
        logger.debug { "Servicio: findPagosByFechaPago()" }

        return pagoRepository.findByFechaPago(fechaPago)?.let {
            Ok(it)
        } ?: Err(PagoErrores.NotFound("Pagos no encontrados para la fecha: $fechaPago"))
    }
}
