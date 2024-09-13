package com.miscuentas.services.tipobalances

import com.github.michaelbull.result.*
import com.miscuentas.errors.TipoBalanceErrores
import com.miscuentas.models.TipoBalance

/**
 * Interfaz que define los servicios relacionados con la entidad TipoBalance.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los tipos de balance en la aplicación.
 */
interface TipoBalanceService {
    /**
     * Obtiene todos los tipos de balance.
     *
     * @return Un `Result` que contiene una lista de tipos de balance o un error de tipo `TipoBalanceErrores`.
     */
    suspend fun getAllTipoBalances(): Result<List<TipoBalance>, TipoBalanceErrores>

    /**
     * Obtiene un tipo de balance por su ID.
     *
     * @param tipo El ID del tipo de balance a buscar.
     * @return Un `Result` que contiene el tipo de balance encontrado o un error de tipo `TipoBalanceErrores`.
     */
    suspend fun getTipoBalanceById(tipo: String): Result<TipoBalance, TipoBalanceErrores>

    /**
     * Agrega un nuevo tipo de balance.
     *
     * @param tipoBalance La entidad de tipo de balance a agregar.
     * @return Un `Result` que contiene el tipo de balance agregado o un error de tipo `TipoBalanceErrores`.
     */
    suspend fun addTipoBalance(tipoBalance: TipoBalance): Result<TipoBalance, TipoBalanceErrores>

    /**
     * Actualiza la información de un tipo de balance existente.
     *
     * @param tipoBalance La entidad de tipo de balance con los datos actualizados.
     * @return Un `Result` que contiene el tipo de balance actualizado o un error de tipo `TipoBalanceErrores`.
     */
    suspend fun updateTipoBalance(tipoBalance: TipoBalance): Result<TipoBalance, TipoBalanceErrores>

    /**
     * Elimina un tipo de balance.
     *
     * @param tipoBalance La entidad de tipo de balance a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `TipoBalanceErrores`.
     */
    suspend fun deleteTipoBalance(tipoBalance: TipoBalance): Result<Boolean, TipoBalanceErrores>
}

