package com.miscuentas.repositories.imagenes

import com.miscuentas.models.Imagen
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `Imagen`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface ImagenRepository: CrudRepository<Imagen, Long> {

    /**
     * Encuentra todas las imágenes asociadas a una hoja específica.
     *
     * @param idHoja El ID de la hoja.
     * @return Una lista de imágenes asociadas a la hoja especificada, o `null` si no se encuentran.
     */
    suspend fun findByHojaId(idHoja: Long): List<Imagen>?
}
