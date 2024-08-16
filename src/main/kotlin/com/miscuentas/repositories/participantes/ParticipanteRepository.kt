package com.miscuentas.repositories.participantes

import com.miscuentas.models.Participante
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `Participante`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface ParticipanteRepository: CrudRepository<Participante, Long> {

    /**
     * Encuentra un participante por su correo electrónico.
     *
     * @param correo El correo electrónico del participante.
     * @return El participante que coincide con el correo especificado, o `null` si no se encuentra.
     */
    suspend fun findByCorreo(correo: String): Participante?

    /**
     * Encuentra todos los participantes asociados a una hoja específica.
     *
     * @param idHoja El ID de la hoja.
     * @return Una lista de participantes asociados a la hoja especificada, o `null` si no se encuentran.
     */
    suspend fun findByHoja(idHoja: Long): List<Participante>?
}

