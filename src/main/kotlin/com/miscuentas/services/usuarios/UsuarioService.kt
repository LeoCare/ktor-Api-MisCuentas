package com.miscuentas.services.usuarios

import com.miscuentas.errors.UsuarioErrores
import com.miscuentas.models.Usuario
import com.github.michaelbull.result.*

/**
 * Interfaz que define los servicios relacionados con la entidad Usuario.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con los usuarios en la aplicación.
 */
interface UsuarioService {

    /**
     * Obtiene un usuario por su ID.
     *
     * @param idUsuario El ID del usuario a buscar.
     * @return Un `Result` que contiene el usuario encontrado o un error de tipo `UsuarioErrores`.
     */
    suspend fun getUsuarioById(idUsuario: Long): Result<Usuario, UsuarioErrores>

    /**
     * Obtiene todos los usuarios.
     *
     * @return Un `Result` que contiene una lista de usuarios o un error de tipo `UsuarioErrores`.
     */
    suspend fun getAllUsuarios(): Result<List<Usuario>, UsuarioErrores>

    /**
     * Obtiene usuarios que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de usuarios encontrados o un error de tipo `UsuarioErrores`.
     */
    suspend fun getUsuariosBy(column: String, query: String): Result<List<Usuario>, UsuarioErrores>

    /**
     * Agrega un nuevo usuario.
     *
     * @param usuario La entidad de usuario a agregar.
     * @return Un `Result` que contiene el usuario agregado o un error de tipo `UsuarioErrores`.
     */
    suspend fun addUsuario(usuario: Usuario): Result<Usuario, UsuarioErrores>

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario La entidad de usuario con los datos actualizados.
     * @return Un `Result` que contiene el usuario actualizado o un error de tipo `UsuarioErrores`.
     */
    suspend fun updateUsuario(usuario: Usuario): Result<Usuario, UsuarioErrores>

    /**
     * Elimina un usuario.
     *
     * @param usuario La entidad de usuario a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `UsuarioErrores`.
     */
    suspend fun deleteUsuario(usuario: Usuario): Result<Boolean, UsuarioErrores>

    /**
     * Guarda una lista de usuarios en la base de datos.
     *
     * @param usuarios La lista de entidades de usuario a guardar.
     * @return Un `Result` que contiene una lista de los usuarios guardados o un error de tipo `UsuarioErrores`.
     */
    suspend fun saveAllUsuarios(usuarios: Iterable<Usuario>): Result<List<Usuario>, UsuarioErrores>

    /**
     * Comprueba si un usuario es administrador.
     *
     * @param id El ID del usuario a comprobar.
     * @return Un `Result` que indica si el usuario es administrador o un error de tipo `UsuarioErrores`.
     */
    suspend fun isAdmin(id: Long): Result<Boolean, UsuarioErrores>

    /**
     * Comprueba el nombre de usuario y la contraseña para autenticar a un usuario.
     *
     * @param correo El correo del usuario.
     * @param contrasenna La contraseña del usuario.
     * @return Un `Result` que contiene el usuario autenticado o un error de tipo `UsuarioErrores`.
     */
    suspend fun checkUserEmailAndPassword(correo: String, contrasenna: String): Result<Usuario, UsuarioErrores>

    /**
     * Comprueba si un correo ya existe en la base de datos.
     *
     * @param correo El correo a comprobar.
     * @return Un `Result` que indica si el correo ya existe o un error de tipo `UsuarioErrores`.
     */
    suspend fun checkCorreoExist(correo: String): Result<Boolean, UsuarioErrores>
}
