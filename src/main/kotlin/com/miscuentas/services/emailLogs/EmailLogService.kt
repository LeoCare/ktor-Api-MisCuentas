package com.miscuentas.services.emailLogs

import com.github.michaelbull.result.*
import com.miscuentas.errors.EmailLogErrores
import com.miscuentas.models.EmailLog

interface EmailLogService {
    suspend fun getEmailLogById(idEmail: Long): Result<EmailLog, EmailLogErrores>
    suspend fun getAllEmailLogs(): Result<List<EmailLog>, EmailLogErrores>
    suspend fun getEmailLogsBy(column: String, query: String): Result<List<EmailLog>, EmailLogErrores>
    suspend fun addEmailLog(emailLog: EmailLog): Result<EmailLog, EmailLogErrores>
    suspend fun updateEmailLog(emailLog: EmailLog): Result<EmailLog, EmailLogErrores>
    suspend fun deleteEmailLog(emailLog: EmailLog): Result<Boolean, EmailLogErrores>
    suspend fun saveAllEmailLogs(emailLogs: Iterable<EmailLog>): Result<List<EmailLog>, EmailLogErrores>
}
