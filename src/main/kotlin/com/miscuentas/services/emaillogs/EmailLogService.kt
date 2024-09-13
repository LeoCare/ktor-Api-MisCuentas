package com.miscuentas.services.emaillogs

import com.github.michaelbull.result.*
import com.miscuentas.errors.EmailLogErrores
import com.miscuentas.models.EmailLog

/**
 * Interfaz que define los servicios relacionados con la entidad EmailLog.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los registros de correos electrónicos en la aplicación.
 */
interface EmailLogService {

    /**
     * Obtiene todos los registros de correos electrónicos.
     *
     * @return Un `Result` que contiene una lista de registros de correos electrónicos o un error de tipo `EmailLogErrores`.
     */
    suspend fun getAllEmailLogs(): Result<List<EmailLog>, EmailLogErrores>

    /**
     * Obtiene un registro de correo electrónico por su ID.
     *
     * @param idEmail El ID del registro de correo electrónico a buscar.
     * @return Un `Result` que contiene el registro de correo electrónico encontrado o un error de tipo `EmailLogErrores`.
     */
    suspend fun getEmailLogById(idEmail: Long): Result<EmailLog, EmailLogErrores>

    /**
     * Obtiene registros de correos electrónicos que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de registros de correos electrónicos encontrados o un error de tipo `EmailLogErrores`.
     */
    suspend fun getEmailLogsBy(column: String, query: String): Result<List<EmailLog>, EmailLogErrores>

    /**
     * Agrega un nuevo registro de correo electrónico.
     *
     * @param emailLog La entidad de registro de correo electrónico a agregar.
     * @return Un `Result` que contiene el registro de correo electrónico agregado o un error de tipo `EmailLogErrores`.
     */
    suspend fun addEmailLog(emailLog: EmailLog): Result<EmailLog, EmailLogErrores>

    /**
     * Actualiza la información de un registro de correo electrónico existente.
     *
     * @param emailLog La entidad de registro de correo electrónico con los datos actualizados.
     * @return Un `Result` que contiene el registro de correo electrónico actualizado o un error de tipo `EmailLogErrores`.
     */
    suspend fun updateEmailLog(emailLog: EmailLog): Result<EmailLog, EmailLogErrores>

    /**
     * Elimina un registro de correo electrónico.
     *
     * @param emailLog La entidad de registro de correo electrónico a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `EmailLogErrores`.
     */
    suspend fun deleteEmailLog(emailLog: EmailLog): Result<Boolean, EmailLogErrores>

    /**
     * Guarda una lista de registros de correos electrónicos en la base de datos.
     *
     * @param emailLogs La lista de entidades de registro de correo electrónico a guardar.
     * @return Un `Result` que contiene una lista de los registros de correos electrónicos guardados o un error de tipo `EmailLogErrores`.
     */
    suspend fun saveAllEmailLogs(emailLogs: Iterable<EmailLog>): Result<List<EmailLog>, EmailLogErrores>

    /**
     * Encuentra registros de correos electrónicos por destinatario.
     *
     * @param destinatario El destinatario de los registros de correos electrónicos a buscar.
     * @return Un `Result` que contiene una lista de registros de correos electrónicos encontrados o un error de tipo `EmailLogErrores`.
     */
    suspend fun findEmailLogsByDestinatario(destinatario: String): Result<List<EmailLog>, EmailLogErrores>

    /**
     * Encuentra registros de correos electrónicos por estado.
     *
     * @param estado El estado de los registros de correos electrónicos a buscar.
     * @return Un `Result` que contiene una lista de registros de correos electrónicos encontrados o un error de tipo `EmailLogErrores`.
     */
    suspend fun findEmailLogsByEstado(estado: String): Result<List<EmailLog>, EmailLogErrores>
}

