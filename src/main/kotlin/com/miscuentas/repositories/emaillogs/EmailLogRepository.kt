package com.miscuentas.repositories.emaillogs

import com.miscuentas.models.EmailLog
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `EmailLog`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface EmailLogRepository: CrudRepository<EmailLog, Long> {

    /**
     * Encuentra todos los registros de correo electrónico por destinatario.
     *
     * @param destinatario El correo electrónico del destinatario.
     * @return Una lista de registros de correo electrónico que coinciden con el destinatario especificado, o `null` si no se encuentran.
     */
    suspend fun findByDestinatario(destinatario: String): List<EmailLog>?

    /**
     * Encuentra todos los registros de correo electrónico por estado.
     *
     * @param estado El estado del correo electrónico (por ejemplo, "enviado", "fallido").
     * @return Una lista de registros de correo electrónico que coinciden con el estado especificado, o `null` si no se encuentran.
     */
    suspend fun findByEstado(estado: String): List<EmailLog>?
}

