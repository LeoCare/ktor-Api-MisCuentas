package com.miscuentas.services.usuarios

import com.miscuentas.entities.UsuariosTable
import com.miscuentas.entities.UsuariosTable.varchar
import com.miscuentas.models.TipoPerfil.Tipo
import com.miscuentas.models.TipoPerfiles
import com.miscuentas.models.Usuario
import com.miscuentas.repositories.usuarios.UsuarioRepository
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Column
import org.koin.java.KoinJavaComponent.inject

class UsuarioServiceImp(
    private val usuarioRepository: UsuarioRepository
): UsuarioService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllUsuarios(): List<Usuario> {
        return usuarioRepository.getAll()
    }

    override suspend fun getUsuarioById(idUsuario: Long): Usuario? {
        return usuarioRepository.getById(idUsuario)
    }

    override suspend fun getUsuariosBy(column: String, query: String): List<Usuario> {
        return usuarioRepository.getAllBy(column, query)
    }

    override suspend fun updateUsuario(usuario: Usuario): Usuario? {
        return usuarioRepository.update(usuario)
    }

    override suspend fun deleteUsuario(usuario: Usuario): Boolean {
        return usuarioRepository.delete(usuario)
    }

    override suspend fun addUsuario(usuario: Usuario): Usuario? {
        return usuarioRepository.save(usuario)
    }

    override suspend fun deleteAllUsuarios(): Boolean {
        return usuarioRepository.deleteAll()
    }

    override suspend fun saveAllUsuarios(usuarios: Iterable<Usuario>): List<Usuario> {
        return usuarioRepository.saveAll(usuarios)
    }

    /** COMPROBAR SI TIENE PERFIL ADMIN **/
    override suspend fun isAdmin(id: Long): Boolean {
        logger.debug { "isAdmin Movil: comprobar si tiene perfil Admin de movil." }
        getUsuarioById(id)?.let {
            return it.perfil == Tipo.ADMIN.name
        }
        return false
    }

    /** COMPROBAR LOGEO **/
    override suspend fun checkUserNameAndPassword(nombre: String, contrasenna: String): Usuario? {
        logger.debug { "CheckUserAndPassword" }
        return usuarioRepository.checkUserNameAndPassword(nombre, contrasenna)
    }
}