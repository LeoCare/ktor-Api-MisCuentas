package com.miscuentas.repositories.gastos

import com.miscuentas.entities.GastosTable
import com.miscuentas.entities.GastosTable.concepto
import com.miscuentas.entities.GastosTable.fechaGasto
import com.miscuentas.entities.GastosTable.idGasto
import com.miscuentas.entities.GastosTable.idImagen
import com.miscuentas.entities.GastosTable.idParticipante
import com.miscuentas.entities.GastosTable.importe
import com.miscuentas.entities.GastosTable.tipo
import com.miscuentas.models.Gasto
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

private val logger = KotlinLogging.logger {}

class GastoRepositoryImpl : GastoRepository {

    private fun resultRowToGasto(resultRow: ResultRow): Gasto {
        return Gasto(
            idGasto = resultRow[idGasto],
            tipo = resultRow[tipo],
            concepto = resultRow[concepto],
            importe = resultRow[importe],
            fechaGasto = resultRow[fechaGasto],
            idParticipante = resultRow[idParticipante],
            idImagen = resultRow[idImagen]
        )
    }

    override suspend fun getAll(): List<Gasto> = dbQuery {
        logger.debug { "Obtener todos los gastos" }
        GastosTable.selectAll().map { resultRowToGasto(it) }
    }

    override suspend fun getById(id: Long): Gasto? = dbQuery {
        GastosTable.select { idGasto eq id }.map { resultRowToGasto(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Gasto> = dbQuery {
        val column = GastosTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        GastosTable.select { column.castTo<String>(TextColumnType()).lowerCase() eq "${q?.lowercase()}" }
            .map { resultRowToGasto(it) }
    }

    override suspend fun update(entity: Gasto): Gasto? = dbQuery {
        GastosTable.update({ idGasto eq entity.idGasto }) {
            it[tipo] = entity.tipo
            it[concepto] = entity.concepto
            it[importe] = entity.importe
            it[fechaGasto] = entity.fechaGasto
            it[idParticipante] = entity.idParticipante
            it[idImagen] = entity.idImagen
        }
        GastosTable.select { idGasto eq entity.idGasto }.map { resultRowToGasto(it) }.singleOrNull()
    }

    override suspend fun save(entity: Gasto): Gasto? = dbQuery {
        val insertStmt = GastosTable.insert {
            it[tipo] = entity.tipo
            it[concepto] = entity.concepto
            it[importe] = entity.importe
            it[fechaGasto] = entity.fechaGasto
            it[idParticipante] = entity.idParticipante
            it[idImagen] = entity.idImagen
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToGasto(it) }
    }

    override suspend fun saveAll(entities: Iterable<Gasto>): List<Gasto> = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: Gasto): Boolean = dbQuery {
        GastosTable.deleteWhere { idGasto eq entity.idGasto } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        GastosTable.deleteAll() > 0
    }

    override suspend fun findByTipo(tipo: String): List<Gasto> = dbQuery {
        GastosTable.select { GastosTable.tipo eq tipo }.map { resultRowToGasto(it) }
    }

    override suspend fun findByParticipante(idParticipante: Long): List<Gasto> = dbQuery {
        GastosTable.select { GastosTable.idParticipante eq idParticipante }.map { resultRowToGasto(it) }
    }
}
