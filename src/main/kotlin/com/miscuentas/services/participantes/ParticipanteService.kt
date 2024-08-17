package com.miscuentas.services.participantes

import com.github.michaelbull.result.*
import com.miscuentas.errors.ParticipanteErrores
import com.miscuentas.models.Participante

/**
 * Interfaz que define los servicios relacionados con la entidad Participante.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los participantes en la aplicación.
 */
interface ParticipanteService {

    /**
     * Obtiene todos los participantes.
     *
     * @return Un `Result` que contiene una lista de participantes o un error de tipo `ParticipanteErrores`.
     */
    suspend fun getAllParticipantes(): Result<List<Participante>, ParticipanteErrores>

    /**
     * Obtiene un participante por su ID.
     *
     * @param idParticipante El ID del participante a buscar.
     * @return Un `Result` que contiene el participante encontrado o un error de tipo `ParticipanteErrores`.
     */
    suspend fun getParticipanteById(idParticipante: Long): Result<Participante, ParticipanteErrores>

    /**
     * Obtiene participantes que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de participantes encontrados o un error de tipo `ParticipanteErrores`.
     */
    suspend fun getParticipantesBy(column: String, query: String): Result<List<Participante>, ParticipanteErrores>

    /**
     * Agrega un nuevo participante.
     *
     * @param participante La entidad de participante a agregar.
     * @return Un `Result` que contiene el participante agregado o un error de tipo `ParticipanteErrores`.
     */
    suspend fun addParticipante(participante: Participante): Result<Participante, ParticipanteErrores>

    /**
     * Actualiza la información de un participante existente.
     *
     * @param participante La entidad de participante con los datos actualizados.
     * @return Un `Result` que contiene el participante actualizado o un error de tipo `ParticipanteErrores`.
     */
    suspend fun updateParticipante(participante: Participante): Result<Participante, ParticipanteErrores>

    /**
     * Elimina un participante.
     *
     * @param participante La entidad de participante a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `ParticipanteErrores`.
     */
    suspend fun deleteParticipante(participante: Participante): Result<Boolean, ParticipanteErrores>

    /**
     * Guarda una lista de participantes en la base de datos.
     *
     * @param participantes La lista de entidades de participante a guardar.
     * @return Un `Result` que contiene una lista de los participantes guardados o un error de tipo `ParticipanteErrores`.
     */
    suspend fun saveAllParticipantes(participantes: Iterable<Participante>): Result<List<Participante>, ParticipanteErrores>

    /**
     * Encuentra participantes por hoja.
     *
     * @param idHoja El ID de la hoja cuyos participantes se desean encontrar.
     * @return Un `Result` que contiene una lista de participantes encontrados o un error de tipo `ParticipanteErrores`.
     */
    suspend fun findParticipantesByHoja(idHoja: Long): Result<List<Participante>, ParticipanteErrores>

    /**
     * Encuentra un participante por correo electrónico.
     *
     * @param correo El correo del participante a buscar.
     * @return Un `Result` que contiene el participante encontrado o un error de tipo `ParticipanteErrores`.
     */
    suspend fun findParticipanteByCorreo(correo: String): Result<Participante, ParticipanteErrores>
}

