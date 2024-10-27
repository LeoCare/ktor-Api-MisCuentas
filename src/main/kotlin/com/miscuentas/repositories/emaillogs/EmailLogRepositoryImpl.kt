package com.miscuentas.repositories.emaillogs

import com.miscuentas.entities.EmailLogTable
import com.miscuentas.models.EmailLog
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

private val logger = KotlinLogging.logger {}

class EmailLogRepositoryImpl : EmailLogRepository {

    private fun resultRowToEmailLog(resultRow: ResultRow): EmailLog {
        return EmailLog(
            idEmail = resultRow[EmailLogTable.idEmail],
            idBalance = resultRow[EmailLogTable.idBalance],
            tipo = resultRow[EmailLogTable.tipo],
            fechaEnvio = resultRow[EmailLogTable.fechaEnvio],
            status = resultRow[EmailLogTable.status]
        )
    }

    override suspend fun getAll(): List<EmailLog> = dbQuery {
        logger.debug { "Obtener todos los registros de correo electrónico" }
        EmailLogTable.selectAll().map { resultRowToEmailLog(it) }
    }

    override suspend fun getById(id: Long): EmailLog? = dbQuery {
        EmailLogTable.select { EmailLogTable.idEmail eq id }
            .map { resultRowToEmailLog(it) }
            .singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<EmailLog> = dbQuery {
        val column = EmailLogTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        EmailLogTable.select { (column.castTo<String>(TextColumnType()).lowerCase() eq "${q?.lowercase()}")}
        .map { resultRowToEmailLog(it) }
    }

    override suspend fun update(entity: EmailLog): EmailLog? = dbQuery {
        EmailLogTable.update({ EmailLogTable.idEmail eq entity.idEmail }) {
            it[idBalance] = entity.idBalance
            it[tipo] = entity.tipo
            it[status] = entity.status
            // No actualizamos fechaEnvio aquí, ya que es la fecha de creación
        }
        getById(entity.idEmail)
    }

    override suspend fun save(entity: EmailLog): EmailLog? = dbQuery {
        val insertStmt = EmailLogTable.insert {
            it[idBalance] = entity.idBalance
            it[tipo] = entity.tipo
            it[fechaEnvio] = entity.fechaEnvio
            it[status] = entity.status
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToEmailLog(it) }
    }

    override suspend fun saveAll(entities: Iterable<EmailLog>): List<EmailLog> = dbQuery {
        entities.forEach { save(it) }
        getAll()
    }

    override suspend fun delete(entity: EmailLog): Boolean = dbQuery {
        EmailLogTable.deleteWhere { EmailLogTable.idEmail eq entity.idEmail } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        EmailLogTable.deleteAll() > 0
    }

}
