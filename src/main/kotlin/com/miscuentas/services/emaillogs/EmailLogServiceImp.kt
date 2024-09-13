package com.miscuentas.services.emaillogs

import com.github.michaelbull.result.*
import com.miscuentas.errors.EmailLogErrores
import com.miscuentas.models.EmailLog
import com.miscuentas.repositories.emaillogs.EmailLogRepository
import mu.KotlinLogging

class EmailLogServiceImp(
    private val emailLogRepository: EmailLogRepository
): EmailLogService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllEmailLogs(): Result<List<EmailLog>, EmailLogErrores> {
        logger.debug { "Servicio: getAllEmailLogs()" }

        return emailLogRepository.getAll()?.let {
            logger.debug { "Servicio: registros de correo electrónico encontrados en el repositorio." }
            Ok(it)
        } ?: Err(EmailLogErrores.NotFound("Registros de correo electrónico no encontrados"))
    }

    override suspend fun getEmailLogById(idEmail: Long): Result<EmailLog, EmailLogErrores> {
        logger.debug { "Servicio: getEmailLogById()" }

        return emailLogRepository.getById(idEmail)?.let {
            logger.debug { "Servicio: registro de correo electrónico encontrado en el repositorio." }
            Ok(it)
        } ?: Err(EmailLogErrores.NotFound("Registro de correo electrónico no encontrado"))
    }

    override suspend fun getEmailLogsBy(column: String, query: String): Result<List<EmailLog>, EmailLogErrores> {
        logger.debug { "Servicio: getEmailLogsBy()" }

        return emailLogRepository.getAllBy(column, query)?.let { emailLogs ->
            logger.debug { "Servicio: registros de correo electrónico encontrados en el repositorio." }
            Ok(emailLogs)
        } ?: Err(EmailLogErrores.NotFound("El registro de correo electrónico: $query no se ha encontrado"))
    }

    override suspend fun updateEmailLog(emailLog: EmailLog): Result<EmailLog, EmailLogErrores> {
        logger.debug { "Servicio: updateEmailLog()" }

        return emailLogRepository.update(emailLog)?.let {
            logger.debug { "Servicio: registro de correo electrónico actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(EmailLogErrores.Forbidden("Registro de correo electrónico no actualizado"))
    }

    override suspend fun deleteEmailLog(emailLog: EmailLog): Result<Boolean, EmailLogErrores> {
        logger.debug { "Servicio: deleteEmailLog()" }

        return if (emailLogRepository.delete(emailLog)) {
            logger.debug { "Servicio: Registro de correo electrónico eliminado correctamente." }
            Ok(true)
        } else Err(EmailLogErrores.NotFound("Registro de correo electrónico no eliminado."))
    }

    override suspend fun addEmailLog(emailLog: EmailLog): Result<EmailLog, EmailLogErrores> {
        logger.debug { "Servicio: addEmailLog()" }

        return emailLogRepository.save(emailLog)?.let {
            logger.debug { "Servicio: registro de correo electrónico guardado desde el repositorio." }
            Ok(it)
        } ?: Err(EmailLogErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun saveAllEmailLogs(emailLogs: Iterable<EmailLog>): Result<List<EmailLog>, EmailLogErrores> {
        logger.debug { "Servicio: saveAllEmailLogs()" }

        return emailLogRepository.saveAll(emailLogs)?.let {
            Ok(it)
        } ?: Err(EmailLogErrores.Forbidden("La inserción ha fallado."))
    }

    override suspend fun findEmailLogsByDestinatario(destinatario: String): Result<List<EmailLog>, EmailLogErrores> {
        logger.debug { "Servicio: findEmailLogsByDestinatario()" }

        return emailLogRepository.findByDestinatario(destinatario)?.let {
            Ok(it)
        } ?: Err(EmailLogErrores.NotFound("Registros de correo electrónico no encontrados para el destinatario: $destinatario"))
    }

    override suspend fun findEmailLogsByEstado(estado: String): Result<List<EmailLog>, EmailLogErrores> {
        logger.debug { "Servicio: findEmailLogsByEstado()" }

        return emailLogRepository.findByEstado(estado)?.let {
            Ok(it)
        } ?: Err(EmailLogErrores.NotFound("Registros de correo electrónico no encontrados para el estado: $estado"))
    }
}
