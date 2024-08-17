package com.miscuentas.services.hojas

import com.github.michaelbull.result.*
import com.miscuentas.errors.HojaErrores
import com.miscuentas.models.Hoja

/**
 * Interfaz que define los servicios relacionados con la entidad Hoja.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con las hojas en la aplicación.
 */
interface HojaService {

    /**
     * Obtiene todas las hojas.
     *
     * @return Un `Result` que contiene una lista de hojas o un error de tipo `HojaErrores`.
     */
    suspend fun getAllHojas(): Result<List<Hoja>, HojaErrores>

    /**
     * Obtiene una hoja por su ID.
     *
     * @param idHoja El ID de la hoja a buscar.
     * @return Un `Result` que contiene la hoja encontrada o un error de tipo `HojaErrores`.
     */
    suspend fun getHojaById(idHoja: Long): Result<Hoja, HojaErrores>

    /**
     * Obtiene hojas que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de hojas encontradas o un error de tipo `HojaErrores`.
     */
    suspend fun getHojasBy(column: String, query: String): Result<List<Hoja>, HojaErrores>

    /**
     * Agrega una nueva hoja.
     *
     * @param hoja La entidad de hoja a agregar.
     * @return Un `Result` que contiene la hoja agregada o un error de tipo `HojaErrores`.
     */
    suspend fun addHoja(hoja: Hoja): Result<Hoja, HojaErrores>

    /**
     * Actualiza la información de una hoja existente.
     *
     * @param hoja La entidad de hoja con los datos actualizados.
     * @return Un `Result` que contiene la hoja actualizada o un error de tipo `HojaErrores`.
     */
    suspend fun updateHoja(hoja: Hoja): Result<Hoja, HojaErrores>

    /**
     * Elimina una hoja.
     *
     * @param hoja La entidad de hoja a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `HojaErrores`.
     */
    suspend fun deleteHoja(hoja: Hoja): Result<Boolean, HojaErrores>

    /**
     * Guarda una lista de hojas en la base de datos.
     *
     * @param hojas La lista de entidades de hoja a guardar.
     * @return Un `Result` que contiene una lista de las hojas guardadas o un error de tipo `HojaErrores`.
     */
    suspend fun saveAllHojas(hojas: Iterable<Hoja>): Result<List<Hoja>, HojaErrores>

    /**
     * Encuentra hojas por estado.
     *
     * @param status El estado de las hojas a buscar.
     * @return Un `Result` que contiene una lista de hojas encontradas o un error de tipo `HojaErrores`.
     */
    suspend fun findHojasByStatus(status: String): Result<List<Hoja>, HojaErrores>

    /**
     * Encuentra hojas por usuario.
     *
     * @param idUsuario El ID del usuario cuyas hojas se desean encontrar.
     * @return Un `Result` que contiene una lista de hojas encontradas o un error de tipo `HojaErrores`.
     */
    suspend fun findHojasByUsuario(idUsuario: Long): Result<List<Hoja>, HojaErrores>
}

