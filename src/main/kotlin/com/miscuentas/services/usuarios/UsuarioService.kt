package com.miscuentas.services.usuarios

import com.miscuentas.errors.UsuarioErrores
import com.miscuentas.models.Usuario
import com.github.michaelbull.result.*

interface UsuarioService {
    suspend fun getUsuarioById(idUsuario: Long): Result<Usuario, UsuarioErrores>
    suspend fun getAllUsuarios(): Result<List<Usuario>, UsuarioErrores>
    suspend fun getUsuariosBy(column: String, query: String): Result<List<Usuario>, UsuarioErrores>
    suspend fun addUsuario(usuario: Usuario): Result<Usuario, UsuarioErrores>
    suspend fun updateUsuario(usuario: Usuario): Result<Usuario, UsuarioErrores>
    suspend fun deleteUsuario(usuario: Usuario): Result<Boolean, UsuarioErrores>
    suspend fun saveAllUsuarios(usuarios: Iterable<Usuario>): Result<List<Usuario>, UsuarioErrores>
    suspend fun isAdmin(id: Long): Result<Boolean, UsuarioErrores>
    suspend fun checkUserEmailAndPassword(correo: String, contrasenna: String): Result<Usuario, UsuarioErrores>
    suspend fun checkCorreoExist(correo: String): Result<Boolean, UsuarioErrores>
}