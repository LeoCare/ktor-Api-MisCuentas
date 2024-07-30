package com.miscuentas.repositories.usuarios

import at.favre.lib.crypto.bcrypt.BCrypt
import com.miscuentas.entities.UsuariosTable
import com.miscuentas.plugins.dbQuery
import com.miscuentas.models.Usuario
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.*
import com.miscuentas.entities.UsuariosTable.contrasenna
import com.miscuentas.entities.UsuariosTable.correo
import com.miscuentas.entities.UsuariosTable.id_usuario
import com.miscuentas.entities.UsuariosTable.nombre
import com.miscuentas.entities.UsuariosTable.perfil
import com.miscuentas.mappers.toResponse
import com.miscuentas.models.TipoPerfil
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
private const val BCRYPT_SALT = 12

class UsuarioRepositoryImpl: UsuarioRepository {

    private fun resultRowToUsuario(resultRow: ResultRow): Usuario {
        return Usuario(
            id_usuario = resultRow[id_usuario],
            nombre = resultRow[nombre],
            correo = resultRow[correo],
            contrasenna = resultRow[contrasenna],
            perfil = resultRow[perfil]
        )
    }

    override fun hashedPassword(contrasenna: String) = Bcrypt.hash(contrasenna, BCRYPT_SALT).decodeToString()

    override suspend fun getAll(): List<Usuario> = dbQuery{
        logger.debug { "Obtener todos" }
        UsuariosTable.selectAll().map { resultRowToUsuario(it) }
    }

    override suspend fun getById(id: Long): Usuario?  = dbQuery{
        UsuariosTable.select { (id_usuario eq id) }.map { resultRowToUsuario(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Usuario>  = dbQuery{
        val column = UsuariosTable.getColumnByName(c)
            ?: throw IllegalArgumentException("La columna $c no existe.")
        UsuariosTable.select { (column.lowerCase() like "%${q?.lowercase()}%")}
            .map { resultRowToUsuario(it) }
    }

    override suspend fun update(entity: Usuario): Usuario? = dbQuery {
        UsuariosTable.update({ id_usuario eq entity.id_usuario }) {
            it[nombre] = entity.nombre
        }
        UsuariosTable.select { (id_usuario eq entity.id_usuario) }.map { resultRowToUsuario(it) }.singleOrNull()
    }

    override suspend fun save(entity: Usuario): Usuario?  = dbQuery{
        val insertStmt = UsuariosTable.insert {
            it[nombre] = entity.nombre
            it[correo] = entity.correo
            it[contrasenna] = hashedPassword(entity.contrasenna)
            it[perfil] = entity.perfil.toString()
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToUsuario(it) }
    }

    override suspend fun saveAll(entities: Iterable<Usuario>): List<Usuario>  = dbQuery{
        entities.forEach { save(it) }
        this.getAll()
    }

    override suspend fun delete(entity: Usuario): Boolean = dbQuery{
        UsuariosTable.deleteWhere { id_usuario eq entity.id_usuario } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery{
        UsuariosTable.deleteAll() > 0
    }

    override suspend fun checkUserNameAndPassword(nombre: String, contrasenna: String): Boolean = dbQuery{
        val usuarios = getAllBy("nombre", nombre) // Lista de Usuarios con el mismo nombre.
        usuarios.any { usuario ->
            Bcrypt.verify(contrasenna, usuario.contrasenna.encodeToByteArray())
        }
    }
}