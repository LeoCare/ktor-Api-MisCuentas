package com.miscuentas.repositories.imagenes

import com.miscuentas.entities.ImagenesTable
import com.miscuentas.entities.ImagenesTable.idImagen
import com.miscuentas.entities.ImagenesTable.imagen

import com.miscuentas.models.Imagen
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

private val logger = KotlinLogging.logger {}

class ImagenRepositoryImpl : ImagenRepository {

    private fun resultRowToImagen(resultRow: ResultRow): Imagen {
        return Imagen(
            idImagen = resultRow[idImagen],
            imagen = resultRow[imagen]
        )
    }

    override suspend fun getAll(): List<Imagen> = dbQuery {
        logger.debug { "Obtener todas las imágenes" }
        ImagenesTable.selectAll().map { resultRowToImagen(it) }
    }

    override suspend fun getById(id: Long): Imagen? = dbQuery {
        ImagenesTable.select { idImagen eq id }.map { resultRowToImagen(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Imagen> = dbQuery {
        val column = ImagenesTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        ImagenesTable.select { column.castTo<String>(TextColumnType()).lowerCase() eq "${q?.lowercase()}" }
            .map { resultRowToImagen(it) }
    }

    override suspend fun update(entity: Imagen): Imagen? = dbQuery {
        ImagenesTable.update({ idImagen eq entity.idImagen }) {
            it[imagen] = entity.imagen
        }
        ImagenesTable.select { idImagen eq entity.idImagen }.map { resultRowToImagen(it) }.singleOrNull()
    }

    override suspend fun save(entity: Imagen): Imagen? = dbQuery {
        val insertStmt = ImagenesTable.insert {
            it[imagen] = entity.imagen
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToImagen(it) }
    }

    override suspend fun saveAll(entities: Iterable<Imagen>): List<Imagen>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: Imagen): Boolean = dbQuery {
        ImagenesTable.deleteWhere { idImagen eq entity.idImagen } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        ImagenesTable.deleteAll() > 0
    }

    override suspend fun findByHojaId(idHoja: Long): List<Imagen>? = dbQuery {
        ImagenesTable.select { idImagen eq idHoja }.map { resultRowToImagen(it) }
    }
}
