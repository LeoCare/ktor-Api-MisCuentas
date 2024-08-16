package com.miscuentas.repositories.balances

import com.miscuentas.entities.TipoBalance
import com.miscuentas.entities.BalancesTable
import com.miscuentas.entities.BalancesTable.idBalance
import com.miscuentas.entities.BalancesTable.idHoja
import com.miscuentas.entities.BalancesTable.idParticipante
import com.miscuentas.entities.BalancesTable.monto
import com.miscuentas.entities.BalancesTable.tipo
import com.miscuentas.models.Balance
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.math.BigDecimal

private val logger = KotlinLogging.logger {}

class BalanceRepositoryImpl : BalanceRepository {

    private fun resultRowToBalance(resultRow: ResultRow): Balance {
        return Balance(
            idBalance = resultRow[idBalance],
            idHoja = resultRow[idHoja],
            idParticipante = resultRow[idParticipante],
            tipo = TipoBalance.fromCodigo(resultRow[tipo]) ?: TipoBalance.DEUDOR,
            monto = resultRow[monto]
        )
    }

    override suspend fun getAll(): List<Balance> = dbQuery {
        logger.debug { "Obtener todos los balances" }
        BalancesTable.selectAll().map { resultRowToBalance(it) }
    }

    override suspend fun getById(id: Long): Balance? = dbQuery {
        BalancesTable.select { idBalance eq id }.map { resultRowToBalance(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Balance> = dbQuery {
        val column = BalancesTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        BalancesTable.select { column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%" }
            .map { resultRowToBalance(it) }
    }

    override suspend fun update(entity: Balance): Balance? = dbQuery {
        BalancesTable.update({ idBalance eq entity.idBalance }) {
            it[idHoja] = entity.idHoja
            it[idParticipante] = entity.idParticipante
            it[tipo] = entity.tipo.codigo
            it[monto] = entity.monto
        }
        BalancesTable.select { idBalance eq entity.idBalance }.map { resultRowToBalance(it) }.singleOrNull()
    }

    override suspend fun save(entity: Balance): Balance? = dbQuery {
        val insertStmt = BalancesTable.insert {
            it[idHoja] = entity.idHoja
            it[idParticipante] = entity.idParticipante
            it[tipo] = entity.tipo.codigo
            it[monto] = entity.monto
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToBalance(it) }
    }

    override suspend fun saveAll(entities: Iterable<Balance>): List<Balance>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: Balance): Boolean = dbQuery {
        BalancesTable.deleteWhere { idBalance eq entity.idBalance } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        BalancesTable.deleteAll() > 0
    }

    override suspend fun findByTipo(tipo: String): List<Balance>? = dbQuery {
        BalancesTable.select { BalancesTable.tipo eq tipo }.map { resultRowToBalance(it) }
    }

    override suspend fun calculateTotalForHoja(idHoja: Long): BigDecimal? = dbQuery {
        BalancesTable.slice(BalancesTable.monto.sum()).select { BalancesTable.idHoja eq idHoja }
            .map { it[BalancesTable.monto.sum()] ?: BigDecimal.ZERO }
            .firstOrNull()
    }
}
