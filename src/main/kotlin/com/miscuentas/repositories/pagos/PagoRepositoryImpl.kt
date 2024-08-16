package com.miscuentas.repositories.pagos

import com.miscuentas.entities.PagosTable
import com.miscuentas.entities.PagosTable.fechaConfirmacion
import com.miscuentas.entities.PagosTable.fechaPago
import com.miscuentas.entities.PagosTable.idBalance
import com.miscuentas.entities.PagosTable.idBalancePagado
import com.miscuentas.entities.PagosTable.idImagen
import com.miscuentas.entities.PagosTable.idPago
import com.miscuentas.entities.PagosTable.monto
import com.miscuentas.models.Pago
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

private val logger = KotlinLogging.logger {}

class PagoRepositoryImpl : PagoRepository {

    private fun resultRowToPago(resultRow: ResultRow): Pago {
        return Pago(
            idPago = resultRow[idPago],
            idBalance = resultRow[idBalance],
            idBalancePagado = resultRow[idBalancePagado],
            monto = resultRow[monto],
            idImagen = resultRow[idImagen],
            fechaPago = resultRow[fechaPago],
            fechaConfirmacion = resultRow[fechaConfirmacion]
        )
    }

    override suspend fun getAll(): List<Pago> = dbQuery {
        logger.debug { "Obtener todos los pagos" }
        PagosTable.selectAll().map { resultRowToPago(it) }
    }

    override suspend fun getById(id: Long): Pago? = dbQuery {
        PagosTable.select { idPago eq id }.map { resultRowToPago(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Pago> = dbQuery {
        val column = PagosTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        PagosTable.select { column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%" }
            .map { resultRowToPago(it) }
    }

    override suspend fun update(entity: Pago): Pago? = dbQuery {
        PagosTable.update({ idPago eq entity.idPago }) {
            it[idBalance] = entity.idBalance
            it[idBalancePagado] = entity.idBalancePagado
            it[monto] = entity.monto
            it[idImagen] = entity.idImagen
            it[fechaPago] = entity.fechaPago
            it[fechaConfirmacion] = entity.fechaConfirmacion
        }
        PagosTable.select { idPago eq entity.idPago }.map { resultRowToPago(it) }.singleOrNull()
    }

    override suspend fun save(entity: Pago): Pago? = dbQuery {
        val insertStmt = PagosTable.insert {
            it[idBalance] = entity.idBalance
            it[idBalancePagado] = entity.idBalancePagado
            it[monto] = entity.monto
            it[idImagen] = entity.idImagen
            it[fechaPago] = entity.fechaPago
            it[fechaConfirmacion] = entity.fechaConfirmacion
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToPago(it) }
    }

    override suspend fun saveAll(entities: Iterable<Pago>): List<Pago>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: Pago): Boolean = dbQuery {
        PagosTable.deleteWhere { idPago eq entity.idPago } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        PagosTable.deleteAll() > 0
    }

    override suspend fun findByBalance(idBalance: Long): List<Pago>? = dbQuery {
        PagosTable.select { PagosTable.idBalance eq idBalance }.map { resultRowToPago(it) }
    }

    override suspend fun findByFechaPago(fechaPago: String): List<Pago>? = dbQuery {
        PagosTable.select { PagosTable.fechaPago.castTo<String>(TextColumnType()) eq fechaPago }.map { resultRowToPago(it) }
    }
}
