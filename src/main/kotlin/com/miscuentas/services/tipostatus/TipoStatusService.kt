package com.miscuentas.services.tipostatus

import com.github.michaelbull.result.*
import com.miscuentas.errors.TipoStatusErrores
import com.miscuentas.models.TipoStatus

/**
 * Interfaz que define los servicios relacionados con la entidad TipoStatus.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los tipos de estado en la aplicación.
 */
interface TipoStatusService {

    /**
     * Obtiene todos los tipos de estado.
     *
     * @return Un `Result` que contiene una lista de tipos de estado o un error de tipo `TipoStatusErrores`.
     */
    suspend fun getAllTipoStatus(): Result<List<TipoStatus>, TipoStatusErrores>

    /**
     * Obtiene un tipo de estado por su ID.
     *
     * @param tipo El ID del tipo de estado a buscar.
     * @return Un `Result` que contiene el tipo de estado encontrado o un error de tipo `TipoStatusErrores`.
     */
    suspend fun getTipoStatusById(tipo: String): Result<TipoStatus, TipoStatusErrores>

    /**
     * Agrega un nuevo tipo de estado.
     *
     * @param tipoStatus La entidad de tipo de estado a agregar.
     * @return Un `Result` que contiene el tipo de estado agregado o un error de tipo `TipoStatusErrores`.
     */
    suspend fun addTipoStatus(tipoStatus: TipoStatus): Result<TipoStatus, TipoStatusErrores>

    /**
     * Actualiza la información de un tipo de estado existente.
     *
     * @param tipoStatus La entidad de tipo de estado con los datos actualizados.
     * @return Un `Result` que contiene el tipo de estado actualizado o un error de tipo `TipoStatusErrores`.
     */
    suspend fun updateTipoStatus(tipoStatus: TipoStatus): Result<TipoStatus, TipoStatusErrores>

    /**
     * Elimina un tipo de estado.
     *
     * @param tipoStatus La entidad de tipo de estado a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `TipoStatusErrores`.
     */
    suspend fun deleteTipoStatus(tipoStatus: TipoStatus): Result<Boolean, TipoStatusErrores>
}

