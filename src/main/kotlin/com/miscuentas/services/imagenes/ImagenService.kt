package com.miscuentas.services.imagenes

import com.github.michaelbull.result.*
import com.miscuentas.errors.ImagenErrores
import com.miscuentas.models.Imagen

/**
 * Interfaz que define los servicios relacionados con la entidad Imagen.
 *
 * Esta interfaz proporciona las operaciones CRUD básicas y otras funcionalidades específicas relacionadas
 * con las imágenes en la aplicación.
 */
interface ImagenService {

    /**
     * Obtiene todas las imágenes.
     *
     * @return Un `Result` que contiene una lista de imágenes o un error de tipo `ImagenErrores`.
     */
    suspend fun getAllImagenes(): Result<List<Imagen>, ImagenErrores>

    /**
     * Obtiene una imagen por su ID.
     *
     * @param idImagen El ID de la imagen a buscar.
     * @return Un `Result` que contiene la imagen encontrada o un error de tipo `ImagenErrores`.
     */
    suspend fun getImagenById(idImagen: Long): Result<Imagen, ImagenErrores>

    /**
     * Obtiene imágenes que coincidan con una columna y un valor de búsqueda específicos.
     *
     * @param column El nombre de la columna en la cual buscar.
     * @param query El valor a buscar en la columna especificada.
     * @return Un `Result` que contiene una lista de imágenes encontradas o un error de tipo `ImagenErrores`.
     */
    suspend fun getImagenesBy(column: String, query: String): Result<List<Imagen>, ImagenErrores>

    /**
     * Agrega una nueva imagen.
     *
     * @param imagen La entidad de imagen a agregar.
     * @return Un `Result` que contiene la imagen agregada o un error de tipo `ImagenErrores`.
     */
    suspend fun addImagen(imagen: Imagen): Result<Imagen, ImagenErrores>

    /**
     * Actualiza la información de una imagen existente.
     *
     * @param imagen La entidad de imagen con los datos actualizados.
     * @return Un `Result` que contiene la imagen actualizada o un error de tipo `ImagenErrores`.
     */
    suspend fun updateImagen(imagen: Imagen): Result<Imagen, ImagenErrores>

    /**
     * Elimina una imagen.
     *
     * @param imagen La entidad de imagen a eliminar.
     * @return Un `Result` que indica si la eliminación fue exitosa o un error de tipo `ImagenErrores`.
     */
    suspend fun deleteImagen(imagen: Imagen): Result<Boolean, ImagenErrores>

    /**
     * Guarda una lista de imágenes en la base de datos.
     *
     * @param imagenes La lista de entidades de imagen a guardar.
     * @return Un `Result` que contiene una lista de las imágenes guardadas o un error de tipo `ImagenErrores`.
     */
    suspend fun saveAllImagenes(imagenes: Iterable<Imagen>): Result<List<Imagen>, ImagenErrores>

    /**
     * Encuentra imágenes por hoja.
     *
     * @param idHoja El ID de la hoja cuyas imágenes se desean encontrar.
     * @return Un `Result` que contiene una lista de imágenes encontradas o un error de tipo `ImagenErrores`.
     */
    suspend fun findImagenesByHoja(idHoja: Long): Result<List<Imagen>, ImagenErrores>
}

