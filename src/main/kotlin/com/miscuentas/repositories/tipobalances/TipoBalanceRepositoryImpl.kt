package com.miscuentas.repositories.tipobalances

import com.miscuentas.entities.TipoBalancesTable
import com.miscuentas.entities.TipoBalancesTable.tipo
import com.miscuentas.models.TipoBalance
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

private val logger = KotlinLogging.logger {}

class TipoBalanceRepositoryImpl : TipoBalanceRepository {

    private fun resultRowToTipoBalance(resultRow: ResultRow): TipoBalance {
        return TipoBalance(
            tipo = resultRow[tipo],
            descripcion = resultRow[TipoBalancesTable.descripcion]
        )
    }

    override suspend fun getAll(): List<TipoBalance>? = dbQuery {
        logger.debug { "Obtener todos los tipos de balance" }
        TipoBalancesTable.selectAll().map { resultRowToTipoBalance(it) }
    }

    override suspend fun getById(id: String): TipoBalance? = dbQuery {
        TipoBalancesTable.select { tipo eq id }.map { resultRowToTipoBalance(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<TipoBalance>? = dbQuery {
        val column = TipoBalancesTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        TipoBalancesTable.select { column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%" }
            .map { resultRowToTipoBalance(it) }
    }

    override suspend fun update(entity: TipoBalance): TipoBalance? = dbQuery {
        TipoBalancesTable.update({ tipo eq entity.tipo }) {
            it[descripcion] = entity.descripcion
        }
        TipoBalancesTable.select { tipo eq entity.tipo }.map { resultRowToTipoBalance(it) }.singleOrNull()
    }

    override suspend fun save(entity: TipoBalance): TipoBalance? = dbQuery {
        val insertStmt = TipoBalancesTable.insert {
            it[tipo] = entity.tipo
            it[descripcion] = entity.descripcion
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToTipoBalance(it) }
    }

    override suspend fun saveAll(entities: Iterable<TipoBalance>): List<TipoBalance>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: TipoBalance): Boolean = dbQuery {
        TipoBalancesTable.deleteWhere { tipo eq entity.tipo } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        TipoBalancesTable.deleteAll() > 0
    }
}
