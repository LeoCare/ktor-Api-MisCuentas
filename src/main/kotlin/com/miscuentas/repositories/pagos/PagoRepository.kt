package com.miscuentas.repositories.pagos

import com.miscuentas.models.Pago
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `Pago`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface PagoRepository: CrudRepository<Pago, Long> {

    /**
     * Encuentra todos los pagos asociados a un balance específico.
     *
     * @param idBalance El ID del balance.
     * @return Una lista de pagos asociados al balance especificado, o `null` si no se encuentran.
     */
    suspend fun findByBalance(idBalance: Long): List<Pago>?

    /**
     * Encuentra todos los pagos realizados en una fecha específica.
     *
     * @param fechaPago La fecha del pago.
     * @return Una lista de pagos realizados en la fecha especificada, o `null` si no se encuentran.
     */
    suspend fun findByFechaPago(fechaPago: String): List<Pago>?
}

