package com.miscuentas.services.tipoperfiles

import com.github.michaelbull.result.*
import com.miscuentas.errors.TipoPerfilErrores
import com.miscuentas.models.TipoPerfil

/**
 * Interfaz que define los servicios relacionados con la entidad TipoPerfil.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los tipos de perfil en la aplicación.
 */
interface TipoPerfilService {

    /**
     * Obtiene todos los tipos de perfil.
     *
     * @return Un `Result` que contiene una lista de tipos de perfil o un error de tipo `TipoPerfilErrores`.
     */
    suspend fun getAllTipoPerfiles(): Result<List<TipoPerfil>, TipoPerfilErrores>

    /**
     * Obtiene un tipo de perfil por su ID.
     *
     * @param tipo El ID del tipo de perfil a buscar.
     * @return Un `Result` que contiene el tipo de perfil encontrado o un error de tipo `TipoPerfilErrores`.
     */
    suspend fun getTipoPerfilById(tipo: String): Result<TipoPerfil, TipoPerfilErrores>

    /**
     * Agrega un nuevo tipo de perfil.
     *
     * @param tipoPerfil La entidad de tipo de perfil a agregar.
     * @return Un `Result` que contiene el tipo de perfil agregado o un error de tipo `TipoPerfilErrores`.
     */
    suspend fun addTipoPerfil(tipoPerfil: TipoPerfil): Result<TipoPerfil, TipoPerfilErrores>

    /**
     * Actualiza la información de un tipo de perfil existente.
     *
     * @param tipoPerfil La entidad de tipo de perfil con los datos actualizados.
     * @return Un `Result` que contiene el tipo de perfil actualizado o un error de tipo `TipoPerfilErrores`.
     */
    suspend fun updateTipoPerfil(tipoPerfil: TipoPerfil): Result<TipoPerfil, TipoPerfilErrores>

    /**
     * Elimina un tipo de perfil.
     *
     * @param tipoPerfil La entidad de tipo de perfil a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `TipoPerfilErrores`.
     */
    suspend fun deleteTipoPerfil(tipoPerfil: TipoPerfil): Result<Boolean, TipoPerfilErrores>
}



