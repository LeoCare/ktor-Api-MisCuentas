package com.miscuentas.services.usuarios

import com.miscuentas.entities.UsuariosTable.varchar
import com.miscuentas.models.Usuario
import com.miscuentas.repositories.usuarios.UsuarioRepository
import org.jetbrains.exposed.sql.Column

class UsuarioServiceImp(
    private val usuarioRepository: UsuarioRepository
): UsuarioService {

    override suspend fun getAllUsuarios(): List<Usuario> {
        return usuarioRepository.getAll()
    }

    override suspend fun getUsuarioById(idUsuario: Long): Usuario? {
        return usuarioRepository.getById(idUsuario)
    }

    override suspend fun getUsuariosBy(column: String, query: String): List<Usuario> {
        val clmn: Column<String> = varchar(column, 255)
        return usuarioRepository.getAllBy(clmn, query)
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
}