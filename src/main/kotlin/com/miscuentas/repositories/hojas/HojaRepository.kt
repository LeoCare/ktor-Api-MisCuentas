package com.miscuentas.repositories.hojas

import com.miscuentas.models.Hoja
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `Hoja`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface HojaRepository: CrudRepository<Hoja, Long> {

    /**
     * Encuentra todas las hojas por estado.
     *
     * @param status El estado de la hoja a buscar.
     * @return Una lista de hojas que coinciden con el estado especificado, o `null` si no se encuentran.
     */
    suspend fun findByStatus(status: String): List<Hoja>?

    /**
     * Encuentra todas las hojas asociadas a un usuario específico.
     *
     * @param idUsuario El ID del usuario.
     * @return Una lista de hojas asociadas al usuario especificado, o `null` si no se encuentran.
     */
    suspend fun findByUsuario(idUsuario: Long): List<Hoja>?
}

