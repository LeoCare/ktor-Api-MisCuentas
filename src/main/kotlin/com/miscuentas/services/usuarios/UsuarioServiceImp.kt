package com.miscuentas.services.usuarios

import com.github.michaelbull.result.*
import com.miscuentas.entities.TipoPerfil
import com.miscuentas.errors.UsuarioErrores
import com.miscuentas.models.Usuario
import com.miscuentas.repositories.usuarios.UsuarioRepository
import mu.KotlinLogging


class UsuarioServiceImp(
    private val usuarioRepository: UsuarioRepository
): UsuarioService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllUsuarios(): Result<List<Usuario>, UsuarioErrores> {
        logger.debug { "Servicio: getAllUsuarios()" }

        return usuarioRepository.getAll()?.let {
            logger.debug { "Servicio: usuarios encontrados en repositorio." }
            Ok(it)
        } ?: Err(UsuarioErrores.NotFound("Usuarios no encontados"))
    }

    override suspend fun getUsuarioById(idUsuario: Long): Result<Usuario, UsuarioErrores> {
        logger.debug { "Servicio: getUsuarioById()" }

        return usuarioRepository.getById(idUsuario)?.let {
            logger.debug { "Servicio: usuario encontrado en repositorio." }
            Ok(it)
        } ?: Err(UsuarioErrores.NotFound("Usuario no encontrado"))
    }

    override suspend fun getUsuariosBy(column: String, query: String): Result<List<Usuario>, UsuarioErrores> {
        logger.debug { "Servicio: getUsuariosBy()" }

        return usuarioRepository.getAllBy(column, query)?.let { usuarios ->
            logger.debug { "Servicio: usuario encontrado en repositorio." }
            Ok(usuarios)
        } ?: Err(UsuarioErrores.NotFound("El usuario: $query no se ha encontrado"))
    }

    override suspend fun updateUsuario(usuario: Usuario): Result<Usuario, UsuarioErrores> {
        logger.debug { "Servicio: updateUsuario()" }

        return usuarioRepository.update(usuario)?.let {
            logger.debug { "Servicio: usuario actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(UsuarioErrores.Forbidden("Usuario no actualizado"))
    }

    override suspend fun updatePass(usuario: Usuario): Result<Usuario, UsuarioErrores> {
        logger.debug { "Servicio: updatePass()" }

        return usuarioRepository.updatePass(usuario)?.let {
            logger.debug { "Servicio: pass del usuario actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(UsuarioErrores.Forbidden("Pass del usuario no actualizado"))
    }

    override suspend fun deleteUsuario(usuario: Usuario): Result<Boolean, UsuarioErrores> {
        logger.debug { "Servicio: deleteUsuario()" }

        return if (usuarioRepository.delete(usuario)) {
            logger.debug { "Servicio: Usuario eliminado correctamente." }
            Ok(true)
        } else Err(UsuarioErrores.NotFound("Usuario no eliminado."))
    }

    override suspend fun addUsuario(usuario: Usuario): Result<Usuario, UsuarioErrores> {
        logger.debug { "Servicio: addUser()" }

        return checkCorreoExist(usuario.correo).mapBoth(
            success = {
                logger.debug { "Servicio: checkCorreoExist() -> correo ya existente" }
                Err(UsuarioErrores.BadRequest("El correo: ${usuario.correo} ya existe."))
            },
            failure = {
                usuarioRepository.save(usuario)?.let {
                    logger.debug { "Servicio: usuario guardado desde el repositorio." }
                    Ok(it)
                } ?: Err(UsuarioErrores.BadRequest("La insercion a fallado."))
            }
        )
    }

    override suspend fun saveAllUsuarios(usuarios: Iterable<Usuario>): Result<List<Usuario>, UsuarioErrores> {
        logger.debug { "Servicio: saveAllUsuarios()" }

        return usuarioRepository.saveAll(usuarios)?.let {
            Ok(it)
        } ?:Err(UsuarioErrores.Forbidden("La insercion a fallado."))

    }

    /** COMPROBAR SI TIENE PERFIL ADMIN **/
    override suspend fun isAdmin(id: Long): Result<Boolean, UsuarioErrores> {
        logger.debug { "Servicio: isAdmin()" }
        return getUsuarioById(id).mapBoth(
            success = {
                logger.debug { "Servicio: Usuario encontrado." }
                if (it.perfil == TipoPerfil.ADMIN) Ok(true)
                else Ok(false)
            },
            failure = { Err(UsuarioErrores.Forbidden("No se ha encontrado ese usuario.")) }
        )
    }

    /** COMPROBAR LOGEO **/
    override suspend fun checkUserEmailAndPassword(correo: String, contrasenna: String): Result<Usuario, UsuarioErrores> {
        logger.debug { "Servicio: CheckUserAndPassword" }

        return checkCorreoExist(correo).mapBoth(
            success = {
                logger.debug { "Servicio: checkCorreoExist() -> correo existe." }
                usuarioRepository.checkUserEmailAndPassword(correo, contrasenna)?.let {
                    Ok(it)
                } ?: Err(UsuarioErrores.NotFound("Usuario no registrado para logeo."))
            },
            failure = {
                logger.debug { "Servicio: checkCorreoExist() -> ese correo no existe" }
                Err(UsuarioErrores.BadRequest("El correo: $correo no existe."))
            }
        )
    }

    override suspend fun checkCorreoExist(correo: String): Result<Usuario, UsuarioErrores> {
        logger.debug { "Servicio: checkCorreoExist()" }
        return usuarioRepository.checkCorreoExist(correo)?.let {
            logger.debug { "Servicio: correo encontrado en repositorio." }
            Ok(it)
        } ?: Err(UsuarioErrores.NotFound("Correo no encontrado"))
    }


    override suspend fun checkCodigoRecupPass(idUsuario: Long, codigo: Long): Boolean {
        return usuarioRepository.checkCodigoRecupPass(idUsuario, codigo)
    }
}