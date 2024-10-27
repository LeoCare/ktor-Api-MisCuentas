package com.miscuentas.repositories.emaillogs

import com.miscuentas.models.EmailLog
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `EmailLog`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface EmailLogRepository: CrudRepository<EmailLog, Long> {
}

