package com.miscuentas.repositories.hojas

import com.miscuentas.entities.HojasTable
import com.miscuentas.entities.HojasTable.fechaCierre
import com.miscuentas.entities.HojasTable.fechaCreacion
import com.miscuentas.entities.HojasTable.idHoja
import com.miscuentas.entities.HojasTable.idUsuario
import com.miscuentas.entities.HojasTable.limiteGastos
import com.miscuentas.entities.HojasTable.status
import com.miscuentas.entities.HojasTable.titulo
import com.miscuentas.entities.TipoStatus
import com.miscuentas.models.Hoja
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

private val logger = KotlinLogging.logger {}

class HojaRepositoryImpl : HojaRepository {

    private fun resultRowToHoja(resultRow: ResultRow): Hoja {
        return Hoja(
            idHoja = resultRow[idHoja],
            titulo = resultRow[titulo],
            fechaCreacion = resultRow[fechaCreacion],
            fechaCierre = resultRow[fechaCierre],
            limiteGastos = resultRow[limiteGastos],
            status = TipoStatus.fromCodigo(resultRow[status]) ?: TipoStatus.ABIERTO,
            idUsuario = resultRow[idUsuario]
        )
    }

    override suspend fun getAll(): List<Hoja> = dbQuery {
        logger.debug { "Obtener todas las hojas" }
        HojasTable.selectAll().map { resultRowToHoja(it) }
    }

    override suspend fun getById(id: Long): Hoja? = dbQuery {
        HojasTable.select { idHoja eq id }.map { resultRowToHoja(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Hoja> = dbQuery {
        val column = HojasTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        HojasTable.select { column.castTo<String>(TextColumnType()).lowerCase() eq "${q?.lowercase()}" }
            .map { resultRowToHoja(it) }
    }

    override suspend fun update(entity: Hoja): Hoja? = dbQuery {
        HojasTable.update({ idHoja eq entity.idHoja }) {
            it[titulo] = entity.titulo
            it[fechaCreacion] = entity.fechaCreacion
            it[fechaCierre] = entity.fechaCierre
            it[limiteGastos] = entity.limiteGastos
            it[status] = entity.status.codigo
            it[idUsuario] = entity.idUsuario
        }
        HojasTable.select { idHoja eq entity.idHoja }.map { resultRowToHoja(it) }.singleOrNull()
    }

    override suspend fun save(entity: Hoja): Hoja? = dbQuery {
        val insertStmt = HojasTable.insert {
            it[titulo] = entity.titulo
            it[fechaCreacion] = entity.fechaCreacion
            it[fechaCierre] = entity.fechaCierre
            it[limiteGastos] = entity.limiteGastos
            it[status] = entity.status.codigo
            it[idUsuario] = entity.idUsuario
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToHoja(it) }
    }

    override suspend fun saveAll(entities: Iterable<Hoja>): List<Hoja>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: Hoja): Boolean = dbQuery {
        HojasTable.deleteWhere { idHoja eq entity.idHoja } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        HojasTable.deleteAll() > 0
    }

    override suspend fun findByStatus(status: String): List<Hoja>? = dbQuery {
        HojasTable.select { HojasTable.status eq status }.map { resultRowToHoja(it) }
    }

    override suspend fun findByUsuario(idUsuario: Long): List<Hoja>? = dbQuery {
        HojasTable.select { HojasTable.idUsuario eq idUsuario }.map { resultRowToHoja(it) }
    }
}
