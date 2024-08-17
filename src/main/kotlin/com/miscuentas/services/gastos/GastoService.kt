package com.miscuentas.services.gastos

import com.miscuentas.errors.GastoErrores
import com.miscuentas.models.Gasto
import com.github.michaelbull.result.*

/**
 * Interfaz que define los servicios relacionados con la entidad Gasto.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los gastos en la aplicación.
 */
interface GastoService {

    /**
     * Obtiene todos los gastos.
     *
     * @return Un `Result` que contiene una lista de gastos o un error de tipo `GastoErrores`.
     */
    suspend fun getAllGastos(): Result<List<Gasto>, GastoErrores>

    /**
     * Obtiene un gasto por su ID.
     *
     * @param idGasto El ID del gasto a buscar.
     * @return Un `Result` que contiene el gasto encontrado o un error de tipo `GastoErrores`.
     */
    suspend fun getGastoById(idGasto: Long): Result<Gasto, GastoErrores>

    /**
     * Obtiene gastos que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de gastos encontrados o un error de tipo `GastoErrores`.
     */
    suspend fun getGastosBy(column: String, query: String): Result<List<Gasto>, GastoErrores>

    /**
     * Agrega un nuevo gasto.
     *
     * @param gasto La entidad de gasto a agregar.
     * @return Un `Result` que contiene el gasto agregado o un error de tipo `GastoErrores`.
     */
    suspend fun addGasto(gasto: Gasto): Result<Gasto, GastoErrores>

    /**
     * Actualiza la información de un gasto existente.
     *
     * @param gasto La entidad de gasto con los datos actualizados.
     * @return Un `Result` que contiene el gasto actualizado o un error de tipo `GastoErrores`.
     */
    suspend fun updateGasto(gasto: Gasto): Result<Gasto, GastoErrores>

    /**
     * Elimina un gasto.
     *
     * @param gasto La entidad de gasto a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `GastoErrores`.
     */
    suspend fun deleteGasto(gasto: Gasto): Result<Boolean, GastoErrores>

    /**
     * Guarda una lista de gastos en la base de datos.
     *
     * @param gastos La lista de entidades de gasto a guardar.
     * @return Un `Result` que contiene una lista de los gastos guardados o un error de tipo `GastoErrores`.
     */
    suspend fun saveAllGastos(gastos: Iterable<Gasto>): Result<List<Gasto>, GastoErrores>

    /**
     * Encuentra gastos por tipo.
     *
     * @param tipo El tipo de gasto a buscar.
     * @return Un `Result` que contiene una lista de gastos encontrados o un error de tipo `GastoErrores`.
     */
    suspend fun findGastosByTipo(tipo: String): Result<List<Gasto>, GastoErrores>

    /**
     * Encuentra gastos por participante.
     *
     * @param idParticipante El ID del participante cuyos gastos se desean encontrar.
     * @return Un `Result` que contiene una lista de gastos encontrados o un error de tipo `GastoErrores`.
     */
    suspend fun findGastosByParticipante(idParticipante: Long): Result<List<Gasto>, GastoErrores>
}

