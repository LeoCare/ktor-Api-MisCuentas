package com.miscuentas.repositories.participantes

import com.miscuentas.entities.ParticipantesTable
import com.miscuentas.entities.ParticipantesTable.correo
import com.miscuentas.entities.ParticipantesTable.idHoja
import com.miscuentas.entities.ParticipantesTable.idParticipante
import com.miscuentas.entities.ParticipantesTable.idUsuario
import com.miscuentas.entities.ParticipantesTable.nombre
import com.miscuentas.models.Participante
import com.miscuentas.plugins.dbQuery
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*


private val logger = KotlinLogging.logger {}

class ParticipanteRepositoryImpl : ParticipanteRepository {

    private fun resultRowToParticipante(resultRow: ResultRow): Participante {
        return Participante(
            idParticipante = resultRow[idParticipante],
            nombre = resultRow[nombre],
            correo = resultRow[correo],
            idUsuario = resultRow[idUsuario],
            idHoja = resultRow[idHoja]
        )
    }

    override suspend fun getAll(): List<Participante> = dbQuery {
        logger.debug { "Obtener todos los participantes" }
        ParticipantesTable.selectAll().map { resultRowToParticipante(it) }
    }

    override suspend fun getById(id: Long): Participante? = dbQuery {
        ParticipantesTable.select { idParticipante eq id }.map { resultRowToParticipante(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Participante> = dbQuery {
        val column = ParticipantesTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        ParticipantesTable.select { column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%" }
            .map { resultRowToParticipante(it) }
    }

    override suspend fun update(entity: Participante): Participante? = dbQuery {
        ParticipantesTable.update({ idParticipante eq entity.idParticipante }) {
            it[nombre] = entity.nombre
            it[correo] = entity.correo
            it[idUsuario] = entity.idUsuario
            it[idHoja] = entity.idHoja
        }
        ParticipantesTable.select { idParticipante eq entity.idParticipante }.map { resultRowToParticipante(it) }.singleOrNull()
    }

    override suspend fun save(entity: Participante): Participante? = dbQuery {
        val insertStmt = ParticipantesTable.insert {
            it[nombre] = entity.nombre
            it[correo] = entity.correo
            it[idUsuario] = entity.idUsuario
            it[idHoja] = entity.idHoja
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToParticipante(it) }
    }

    override suspend fun saveAll(entities: Iterable<Participante>): List<Participante>? = dbQuery {
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: Participante): Boolean = dbQuery {
        ParticipantesTable.deleteWhere { idParticipante eq entity.idParticipante } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        ParticipantesTable.deleteAll() > 0
    }

    override suspend fun findByCorreo(correo: String): Participante? = dbQuery {
        ParticipantesTable.select { ParticipantesTable.correo eq correo }
            .map { resultRowToParticipante(it) }
            .singleOrNull()
    }

    override suspend fun findByHoja(idHoja: Long): List<Participante>? = dbQuery {
        ParticipantesTable.select { ParticipantesTable.idHoja eq idHoja }.map { resultRowToParticipante(it) }
    }
}
