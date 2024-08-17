package com.miscuentas.services.pagos

import com.github.michaelbull.result.*
import com.miscuentas.errors.PagoErrores
import com.miscuentas.models.Pago

/**
 * Interfaz que define los servicios relacionados con la entidad Pago.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los pagos en la aplicación.
 */
interface PagoService {

    /**
     * Obtiene todos los pagos.
     *
     * @return Un `Result` que contiene una lista de pagos o un error de tipo `PagoErrores`.
     */
    suspend fun getAllPagos(): Result<List<Pago>, PagoErrores>

    /**
     * Obtiene un pago por su ID.
     *
     * @param idPago El ID del pago a buscar.
     * @return Un `Result` que contiene el pago encontrado o un error de tipo `PagoErrores`.
     */
    suspend fun getPagoById(idPago: Long): Result<Pago, PagoErrores>

    /**
     * Obtiene pagos que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de pagos encontrados o un error de tipo `PagoErrores`.
     */
    suspend fun getPagosBy(column: String, query: String): Result<List<Pago>, PagoErrores>

    /**
     * Agrega un nuevo pago.
     *
     * @param pago La entidad de pago a agregar.
     * @return Un `Result` que contiene el pago agregado o un error de tipo `PagoErrores`.
     */
    suspend fun addPago(pago: Pago): Result<Pago, PagoErrores>

    /**
     * Actualiza la información de un pago existente.
     *
     * @param pago La entidad de pago con los datos actualizados.
     * @return Un `Result` que contiene el pago actualizado o un error de tipo `PagoErrores`.
     */
    suspend fun updatePago(pago: Pago): Result<Pago, PagoErrores>

    /**
     * Elimina un pago.
     *
     * @param pago La entidad de pago a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `PagoErrores`.
     */
    suspend fun deletePago(pago: Pago): Result<Boolean, PagoErrores>

    /**
     * Guarda una lista de pagos en la base de datos.
     *
     * @param pagos La lista de entidades de pago a guardar.
     * @return Un `Result` que contiene una lista de los pagos guardados o un error de tipo `PagoErrores`.
     */
    suspend fun saveAllPagos(pagos: Iterable<Pago>): Result<List<Pago>, PagoErrores>

    /**
     * Encuentra pagos por balance.
     *
     * @param idBalance El ID del balance cuyos pagos se desean encontrar.
     * @return Un `Result` que contiene una lista de pagos encontrados o un error de tipo `PagoErrores`.
     */
    suspend fun findPagosByBalance(idBalance: Long): Result<List<Pago>, PagoErrores>

    /**
     * Encuentra pagos por fecha de pago.
     *
     * @param fechaPago La fecha de pago de los pagos a buscar.
     * @return Un `Result` que contiene una lista de pagos encontrados o un error de tipo `PagoErrores`.
     */
    suspend fun findPagosByFechaPago(fechaPago: String): Result<List<Pago>, PagoErrores>
}

