package com.miscuentas.repositories.gastos

import com.miscuentas.models.Gasto
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `Gasto`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface GastoRepository: CrudRepository<Gasto, Long> {

    /**
     * Encuentra todos los gastos por tipo.
     *
     * @param tipo El tipo de gasto a buscar.
     * @return Una lista de gastos que coinciden con el tipo especificado, o `null` si no se encuentran.
     */
    suspend fun findByTipo(tipo: String): List<Gasto>?

    /**
     * Encuentra todos los gastos asociados a un participante específico.
     *
     * @param idParticipante El ID del participante.
     * @return Una lista de gastos asociados al participante especificado, o `null` si no se encuentran.
     */
    suspend fun findByParticipante(idParticipante: Long): List<Gasto>?
}

