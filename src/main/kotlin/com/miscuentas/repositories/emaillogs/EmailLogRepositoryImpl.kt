package com.miscuentas.repositories.emaillogs

import com.miscuentas.entities.EmailLogTable
import com.miscuentas.entities.EmailLogTable.asunto
import com.miscuentas.entities.EmailLogTable.contenido
import com.miscuentas.entities.EmailLogTable.destinatario
import com.miscuentas.entities.EmailLogTable.estado
import com.miscuentas.entities.EmailLogTable.fechaEnvio
import com.miscuentas.entities.EmailLogTable.idEmail
import com.miscuentas.entities.EmailLogTable.idPago
import com.miscuentas.entities.EmailLogTable.idParticipante
import com.miscuentas.entities.EmailLogTable.tipo
import com.miscuentas.models.EmailLog
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

private val logger = KotlinLogging.logger {}

class EmailLogRepositoryImpl : EmailLogRepository {

    private fun resultRowToEmailLog(resultRow: ResultRow): EmailLog {
        return EmailLog(
            idEmail = resultRow[idEmail],
            idParticipante = resultRow[idParticipante],
            idPago = resultRow[idPago],
            tipo = resultRow[tipo],
            destinatario = resultRow[destinatario],
            asunto = resultRow[asunto],
            contenido = resultRow[contenido],
            fechaEnvio = resultRow[fechaEnvio].toString(),
            estado = resultRow[estado]
        )
    }

    override suspend fun getAll(): List<EmailLog> = dbQuery {
        logger.debug { "Obtener todos los registros de correo electrónico" }
        EmailLogTable.selectAll().map { resultRowToEmailLog(it) }
    }

    override suspend fun getById(id: Long): EmailLog? = dbQuery {
        EmailLogTable.select { idEmail eq id }.map { resultRowToEmailLog(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<EmailLog> = dbQuery {
        val column = EmailLogTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        EmailLogTable.select { column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%" }
            .map { resultRowToEmailLog(it) }
    }

    override suspend fun update(entity: EmailLog): EmailLog? = dbQuery {
        EmailLogTable.update({ idEmail eq entity.idEmail }) {
            it[idParticipante] = entity.idParticipante
            it[idPago] = entity.idPago
            it[tipo] = entity.tipo
            it[destinatario] = entity.destinatario
            it[asunto] = entity.asunto
            it[contenido] = entity.contenido
            it[estado] = entity.estado
        }
        EmailLogTable.select { idEmail eq entity.idEmail }.map { resultRowToEmailLog(it) }.singleOrNull()
    }

    override suspend fun save(entity: EmailLog): EmailLog? = dbQuery {
        val insertStmt = EmailLogTable.insert {
            it[idParticipante] = entity.idParticipante
            it[idPago] = entity.idPago
            it[tipo] = entity.tipo
            it[destinatario] = entity.destinatario
            it[asunto] = entity.asunto
            it[contenido] = entity.contenido
            it[estado] = entity.estado
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToEmailLog(it) }
    }

    override suspend fun saveAll(entities: Iterable<EmailLog>): List<EmailLog>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: EmailLog): Boolean = dbQuery {
        EmailLogTable.deleteWhere { idEmail eq entity.idEmail } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        EmailLogTable.deleteAll() > 0
    }

    override suspend fun findByDestinatario(destinatario: String): List<EmailLog>? = dbQuery {
        EmailLogTable.select { EmailLogTable.destinatario eq destinatario }.map { resultRowToEmailLog(it) }
    }

    override suspend fun findByEstado(estado: String): List<EmailLog>? = dbQuery {
        EmailLogTable.select { EmailLogTable.estado eq estado }.map { resultRowToEmailLog(it) }
    }
}
