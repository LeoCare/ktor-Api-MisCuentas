package com.miscuentas.repositories.tipoPerfiles

import com.miscuentas.entities.TipoPerfilesTable
import com.miscuentas.entities.TipoPerfilesTable.tipo
import com.miscuentas.models.TipoPerfil
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

private val logger = KotlinLogging.logger {}

class TipoPerfilRepositoryImpl : TipoPerfilRepository {

    private fun resultRowToTipoPerfil(resultRow: ResultRow): TipoPerfil {
        return TipoPerfil(
            tipo = resultRow[tipo],
            descripcion = resultRow[TipoPerfilesTable.descripcion]
        )
    }

    override suspend fun getAll(): List<TipoPerfil>? = dbQuery {
        logger.debug { "Obtener todos los tipos de perfil" }
        TipoPerfilesTable.selectAll().map { resultRowToTipoPerfil(it) }
    }

    override suspend fun getById(id: String): TipoPerfil? = dbQuery {
        TipoPerfilesTable.select { tipo eq id }.map { resultRowToTipoPerfil(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<TipoPerfil>? = dbQuery {
        val column = TipoPerfilesTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        TipoPerfilesTable.select { column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%" }
            .map { resultRowToTipoPerfil(it) }
    }

    override suspend fun update(entity: TipoPerfil): TipoPerfil? = dbQuery {
        TipoPerfilesTable.update({ tipo eq entity.tipo }) {
            it[descripcion] = entity.descripcion
        }
        TipoPerfilesTable.select { tipo eq entity.tipo }.map { resultRowToTipoPerfil(it) }.singleOrNull()
    }

    override suspend fun save(entity: TipoPerfil): TipoPerfil? = dbQuery {
        val insertStmt = TipoPerfilesTable.insert {
            it[tipo] = entity.tipo
            it[descripcion] = entity.descripcion
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToTipoPerfil(it) }
    }

    override suspend fun saveAll(entities: Iterable<TipoPerfil>): List<TipoPerfil>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: TipoPerfil): Boolean = dbQuery {
        TipoPerfilesTable.deleteWhere { tipo eq entity.tipo } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        TipoPerfilesTable.deleteAll() > 0
    }
}
