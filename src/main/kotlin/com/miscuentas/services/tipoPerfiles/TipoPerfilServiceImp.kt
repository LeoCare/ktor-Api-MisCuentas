package com.miscuentas.services.tipoPerfiles

import com.github.michaelbull.result.*
import com.miscuentas.errors.TipoPerfilErrores
import com.miscuentas.models.TipoPerfil
import com.miscuentas.repositories.tipoPerfiles.TipoPerfilRepository
import mu.KotlinLogging

class TipoPerfilServiceImp(
    private val tipoPerfilRepository: TipoPerfilRepository
): TipoPerfilService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllTipoPerfiles(): Result<List<TipoPerfil>, TipoPerfilErrores> {
        logger.debug { "Servicio: getAllTipoPerfiles()" }

        return tipoPerfilRepository.getAll()?.let {
            logger.debug { "Servicio: tipos de perfil encontrados en repositorio." }
            Ok(it)
        } ?: Err(TipoPerfilErrores.NotFound("Tipos de perfil no encontrados"))
    }

    override suspend fun getTipoPerfilById(tipo: String): Result<TipoPerfil, TipoPerfilErrores> {
        logger.debug { "Servicio: getTipoPerfilById()" }

        return tipoPerfilRepository.getById(tipo)?.let {
            logger.debug { "Servicio: tipo de perfil encontrado en repositorio." }
            Ok(it)
        } ?: Err(TipoPerfilErrores.NotFound("Tipo de perfil no encontrado"))
    }

    override suspend fun addTipoPerfil(tipoPerfil: TipoPerfil): Result<TipoPerfil, TipoPerfilErrores> {
        logger.debug { "Servicio: addTipoPerfil()" }

        return tipoPerfilRepository.save(tipoPerfil)?.let {
            logger.debug { "Servicio: tipo de perfil guardado desde el repositorio." }
            Ok(it)
        } ?: Err(TipoPerfilErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun updateTipoPerfil(tipoPerfil: TipoPerfil): Result<TipoPerfil, TipoPerfilErrores> {
        logger.debug { "Servicio: updateTipoPerfil()" }

        return tipoPerfilRepository.update(tipoPerfil)?.let {
            logger.debug { "Servicio: tipo de perfil actualizado desde el repositorio." }
            Ok(it)
        } ?: Err(TipoPerfilErrores.Forbidden("Tipo de perfil no actualizado"))
    }

    override suspend fun deleteTipoPerfil(tipoPerfil: TipoPerfil): Result<Boolean, TipoPerfilErrores> {
        logger.debug { "Servicio: deleteTipoPerfil()" }

        return if (tipoPerfilRepository.delete(tipoPerfil)) {
            logger.debug { "Servicio: Tipo de perfil eliminado correctamente." }
            Ok(true)
        } else Err(TipoPerfilErrores.NotFound("Tipo de perfil no eliminado."))
    }
}
