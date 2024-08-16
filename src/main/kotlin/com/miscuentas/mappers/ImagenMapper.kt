package com.miscuentas.mappers

import com.miscuentas.dto.ImagenDto
import com.miscuentas.models.Imagen

/**
 * Extensión para convertir una instancia de `Imagen` a `ImagenDto`.
 *
 * @return Una instancia de `ImagenDto` con los datos de la `Imagen`.
 */
fun Imagen.toDto() = ImagenDto(
    idImagen = this.idImagen,
    imagen = this.imagen.toString(Charsets.UTF_8) // Convertimos el BLOB a String
)

/**
 * Extensión para convertir una lista de `Imagen` a una lista de `ImagenDto`.
 *
 * @return Una lista de instancias `ImagenDto` correspondientes a la lista de `Imagen`.
 */
fun List<Imagen>.toDto() = this.map { it.toDto() }

/**
 * Extensión para convertir una instancia de `ImagenDto` a `Imagen`.
 *
 * @return Una instancia de `Imagen` con los datos del `ImagenDto`.
 */
fun ImagenDto.toModel() = Imagen(
    idImagen = this.idImagen,
    imagen = this.imagen.toByteArray(Charsets.UTF_8) // Convertimos el String a BLOB
)
