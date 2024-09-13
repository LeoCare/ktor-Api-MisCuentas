package com.miscuentas.repositories.usuarios

import at.favre.lib.crypto.bcrypt.BCrypt
import com.miscuentas.entities.TipoPerfil
import com.miscuentas.entities.UsuariosTable
import com.miscuentas.entities.UsuariosTable.contrasenna
import com.miscuentas.entities.UsuariosTable.correo
import com.miscuentas.entities.UsuariosTable.id_usuario
import com.miscuentas.entities.UsuariosTable.nombre
import com.miscuentas.entities.UsuariosTable.perfil
import com.miscuentas.models.Usuario
import com.miscuentas.plugins.dbQuery
import com.toxicbakery.bcrypt.Bcrypt
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

private val logger = KotlinLogging.logger {}
private const val BCRYPT_SALT = 12

/** IMPLEMENTACION DEL REPOSITORIO PARA LA CLASE USUARIO:
 * Hereda de UsuarioRepository
 */
class UsuarioRepositoryImpl: UsuarioRepository {

    /** resultRowToUsuario() mapea el resultado devuelto a la clase Usuario.
     * @param resultRow resultado devuelto de la BBDD.
     * @return un tipo Usuario.
     */
    private fun resultRowToUsuario(resultRow: ResultRow): Usuario {
        return Usuario(
            idUsuario = resultRow[id_usuario],
            nombre = resultRow[nombre],
            correo = resultRow[correo],
            contrasenna = resultRow[contrasenna],
            perfil = TipoPerfil.fromCodigo(resultRow[perfil]) ?: TipoPerfil.USER
        )
    }

    override fun hashedPassword(contrasenna: String): String {
        // Genera el hash con un cost de 12 (puedes ajustarlo según tu necesidad)
        return BCrypt.withDefaults().hashToString(BCRYPT_SALT, contrasenna.toCharArray())
    }

    override suspend fun getAll(): List<Usuario> = dbQuery{
        logger.debug { "Obtener todos" }
        UsuariosTable.selectAll().map { resultRowToUsuario(it) }
    }

    override suspend fun getById(id: Long): Usuario?  = dbQuery{
        UsuariosTable.select { (id_usuario eq id) }.map { resultRowToUsuario(it) }.singleOrNull()
    }

    override suspend fun getAllBy(c: String, q: String?): List<Usuario>  = dbQuery{
        val column = UsuariosTable.columns.find { it.name == c }
            ?: throw IllegalArgumentException("La columna $c no existe.")
        UsuariosTable.select { (column.castTo<String>(TextColumnType()).lowerCase() like "%${q?.lowercase()}%")}
            .map { resultRowToUsuario(it) }
    }

    override suspend fun update(entity: Usuario): Usuario? = dbQuery {
        UsuariosTable.update({ id_usuario eq entity.idUsuario }) {
            it[nombre] = entity.nombre
            it[correo] = entity.correo
            it[contrasenna] = entity.contrasenna
            it[perfil] = entity.perfil.codigo
        }
        UsuariosTable.select { (id_usuario eq entity.idUsuario) }.map { resultRowToUsuario(it) }.singleOrNull()
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
        UsuariosTable.deleteWhere { id_usuario eq entity.idUsuario } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery{
        UsuariosTable.deleteAll() > 0
    }

    override suspend fun checkUserEmailAndPassword(correo: String, contrasenna: String): Usuario? = dbQuery{
        val usuarios = getAllBy("correo", correo) // Lista de Usuarios con el mismo correo.
        for (usuario in usuarios) {
            if (Bcrypt.verify(contrasenna, usuario.contrasenna.encodeToByteArray())){
                return@dbQuery usuario
            }
        }
        return@dbQuery null
    }

    override suspend fun checkCorreoExist(correo: String): Usuario? = dbQuery {
        UsuariosTable.select(UsuariosTable.correo eq correo).map { resultRowToUsuario(it) }.firstOrNull()
    }
}