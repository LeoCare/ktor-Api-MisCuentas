package com.miscuentas.services.imagenes

import com.github.michaelbull.result.*
import com.miscuentas.errors.ImagenErrores
import com.miscuentas.models.Imagen
import com.miscuentas.repositories.imagenes.ImagenRepository
import mu.KotlinLogging
import java.math.BigDecimal

class ImagenServiceImp(
    private val imagenRepository: ImagenRepository
): ImagenService {

    private val logger = KotlinLogging.logger {}

    override suspend fun getAllImagenes(): Result<List<Imagen>, ImagenErrores> {
        logger.debug { "Servicio: getAllImagenes()" }

        return imagenRepository.getAll()?.let {
            logger.debug { "Servicio: imágenes encontradas en el repositorio." }
            Ok(it)
        } ?: Err(ImagenErrores.NotFound("Imágenes no encontradas"))
    }

    override suspend fun getImagenById(idImagen: Long): Result<Imagen, ImagenErrores> {
        logger.debug { "Servicio: getImagenById()" }

        return imagenRepository.getById(idImagen)?.let {
            logger.debug { "Servicio: imagen encontrada en el repositorio." }
            Ok(it)
        } ?: Err(ImagenErrores.NotFound("Imagen no encontrada"))
    }

    override suspend fun getImagenesBy(column: String, query: String): Result<List<Imagen>, ImagenErrores> {
        logger.debug { "Servicio: getImagenesBy()" }

        return imagenRepository.getAllBy(column, query)?.let { imagenes ->
            logger.debug { "Servicio: imágenes encontradas en el repositorio." }
            Ok(imagenes)
        } ?: Err(ImagenErrores.NotFound("La imagen: $query no se ha encontrado"))
    }

    override suspend fun updateImagen(imagen: Imagen): Result<Imagen, ImagenErrores> {
        logger.debug { "Servicio: updateImagen()" }

        return imagenRepository.update(imagen)?.let {
            logger.debug { "Servicio: imagen actualizada desde el repositorio." }
            Ok(it)
        } ?: Err(ImagenErrores.Forbidden("Imagen no actualizada"))
    }

    override suspend fun deleteImagen(imagen: Imagen): Result<Boolean, ImagenErrores> {
        logger.debug { "Servicio: deleteImagen()" }

        return if (imagenRepository.delete(imagen)) {
            logger.debug { "Servicio: Imagen eliminada correctamente." }
            Ok(true)
        } else Err(ImagenErrores.NotFound("Imagen no eliminada."))
    }

    override suspend fun addImagen(imagen: Imagen): Result<Imagen, ImagenErrores> {
        logger.debug { "Servicio: addImagen()" }

        return imagenRepository.save(imagen)?.let {
            logger.debug { "Servicio: imagen guardada desde el repositorio." }
            Ok(it)
        } ?: Err(ImagenErrores.BadRequest("La inserción ha fallado."))
    }

    override suspend fun saveAllImagenes(imagenes: Iterable<Imagen>): Result<List<Imagen>, ImagenErrores> {
        logger.debug { "Servicio: saveAllImagenes()" }

        return imagenRepository.saveAll(imagenes)?.let {
            Ok(it)
        } ?: Err(ImagenErrores.Forbidden("La inserción ha fallado."))
    }

    override suspend fun findImagenesByHoja(idHoja: Long): Result<List<Imagen>, ImagenErrores> {
        logger.debug { "Servicio: findImagenesByHoja()" }

        return imagenRepository.findByHojaId(idHoja)?.let {
            Ok(it)
        } ?: Err(ImagenErrores.NotFound("Imágenes no encontradas para la hoja: $idHoja"))
    }
}
