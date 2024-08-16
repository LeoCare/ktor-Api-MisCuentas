package com.miscuentas.services.imagenes

import com.github.michaelbull.result.*
import com.miscuentas.errors.ImagenErrores
import com.miscuentas.models.Imagen

interface ImagenService {
    suspend fun getImagenById(idImagen: Long): Result<Imagen, ImagenErrores>
    suspend fun getAllImagenes(): Result<List<Imagen>, ImagenErrores>
    suspend fun addImagen(imagen: Imagen): Result<Imagen, ImagenErrores>
    suspend fun updateImagen(imagen: Imagen): Result<Imagen, ImagenErrores>
    suspend fun deleteImagen(imagen: Imagen): Result<Boolean, ImagenErrores>
    suspend fun saveAllImagenes(imagenes: Iterable<Imagen>): Result<List<Imagen>, ImagenErrores>
}
