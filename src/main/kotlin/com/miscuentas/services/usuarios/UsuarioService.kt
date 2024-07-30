package com.miscuentas.services.usuarios

import com.miscuentas.models.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioService {
    suspend fun getUsuarioById(idUsuario: Long): Usuario?
    suspend fun getAllUsuarios(): List<Usuario>
    suspend fun getUsuariosBy(column: String, query: String): List<Usuario>
    suspend fun addUsuario(usuario: Usuario): Usuario?
    suspend fun updateUsuario(usuario: Usuario): Usuario?
    suspend fun deleteUsuario(usuario: Usuario): Boolean
    suspend fun deleteAllUsuarios(): Boolean
    suspend fun saveAllUsuarios(usuarios: Iterable<Usuario>): List<Usuario>
    suspend fun isAdmin(id: Long): Boolean
    suspend fun checkUserNameAndPassword(nombre: String, contrasenna: String): Boolean
}