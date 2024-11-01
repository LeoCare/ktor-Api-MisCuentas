package com.miscuentas.repositories.usuarios

import com.miscuentas.models.Usuario
import com.miscuentas.plugins.dbQuery
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `Usuario`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface UsuarioRepository: CrudRepository<Usuario, Long> {
    /**
     * Genera un hash seguro para una contraseña dada.
     *
     * @param entity usuario con la contraseña actualizada.
     * @return El usuario con la pass actualizada.
     */
    suspend fun updatePass(entity: Usuario): Usuario?

    /**
     * Genera un hash seguro para una contraseña dada.
     *
     * @param contrasenna La contraseña a ser hasheada.
     * @return El hash generado de la contraseña.
     */
    fun hashedPassword(contrasenna: String): String

    /**
     * Verifica si el correo y la contraseña proporcionados coinciden con un usuario existente.
     *
     * @param correo El correo electrónico del usuario.
     * @param contrasenna La contraseña del usuario.
     * @return El usuario que coincide con el correo y la contraseña, o `null` si no coincide.
     */
    suspend fun checkUserEmailAndPassword(correo: String, contrasenna: String): Usuario?

    /**
     * Verifica si un correo electrónico ya está registrado en el sistema.
     *
     * @param correo El correo electrónico a verificar.
     * @return `true` si el correo ya existe, `false` si no existe.
     */
    suspend fun checkCorreoExist(correo: String): Usuario?


    /**
     * Verifica si el codigo de recuperacion es valido para ese usuario.
     *
     * @param idUsuario id del usuario que pide recuperar.
     * @param codigo codigo dado por el usuario.
     * @return `true` si el codigo es valido, `false` si no.
     */
    suspend fun checkCodigoRecupPass(idUsuario: Long, codigo: Long): Boolean
}