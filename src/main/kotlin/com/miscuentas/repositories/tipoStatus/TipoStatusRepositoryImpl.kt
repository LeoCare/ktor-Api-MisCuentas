package com.miscuentas.repositories.tipoStatus

import com.miscuentas.entities.TipoStatusTable
import com.miscuentas.entities.TipoStatusTable.tipo
import com.miscuentas.models.TipoStatus
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

private val logger = KotlinLogging.logger {}

class TipoStatusRepositoryImpl : TipoStatusRepository {

    private fun resultRowToTipoStatus(resultRow: ResultRow): TipoStatus {
        return TipoStatus(
            tipo = resultRow[tipo],
            descripcion = resultRow[TipoStatusTable.descripcion]
        )
    }

    override suspend fun getAll(): List<TipoStatus>? = dbQuery {
        logger.debug { "Obtener todos los tipos de estado" }
        TipoStatusTable.selectAll().map { resultRowToTipoStatus(it) }
    }

    override suspend fun getById(id: String): TipoStatus? = dbQuery {
        TipoStatusTable.select { tipo eq id }.map { resultRowToTipoStatus(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<TipoStatus>? = dbQuery {
        val column = TipoStatusTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        TipoStatusTable.select { column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%" }
            .map { resultRowToTipoStatus(it) }
    }

    override suspend fun update(entity: TipoStatus): TipoStatus? = dbQuery {
        TipoStatusTable.update({ tipo eq entity.tipo }) {
            it[descripcion] = entity.descripcion
        }
        TipoStatusTable.select { tipo eq entity.tipo }.map { resultRowToTipoStatus(it) }.singleOrNull()
    }

    override suspend fun save(entity: TipoStatus): TipoStatus? = dbQuery {
        val insertStmt = TipoStatusTable.insert {
            it[tipo] = entity.tipo
            it[descripcion] = entity.descripcion
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToTipoStatus(it) }
    }

    override suspend fun saveAll(entities: Iterable<TipoStatus>): List<TipoStatus>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: TipoStatus): Boolean = dbQuery {
        TipoStatusTable.deleteWhere { tipo eq entity.tipo } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        TipoStatusTable.deleteAll() > 0
    }
}
