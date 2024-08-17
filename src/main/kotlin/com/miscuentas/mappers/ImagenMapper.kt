package com.miscuentas.mappers

import com.miscuentas.dto.ImagenCrearDto
import com.miscuentas.dto.ImagenDto
import com.miscuentas.models.Imagen
import java.util.Base64
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

/**
 * Extensión para convertir una instancia de `Imagen` a `ImagenDto`.
 *
 * @return Una instancia de `ImagenDto` con los datos de la `Imagen`.
 */
fun Imagen.toDto() = ImagenDto(
    idImagen = this.idImagen,
    imagen = Base64.getEncoder().encodeToString(this.imagen.bytes) // Convertimos el BLOB a String usando Base64
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
    imagen = ExposedBlob(Base64.getDecoder().decode(this.imagen)) // Convertimos el String a BLOB usando Base64
)

/**
 * Extensión para convertir una instancia de `ImagenCrearDto` a `Imagen`.
 *
 * @return Una instancia de `Imagen` con los datos del `ImagenCrearDto`.
 */
fun ImagenCrearDto.toModel() = Imagen(
    idImagen = 0,
    imagen = ExposedBlob(Base64.getDecoder().decode(this.imagen)) // Convertimos el String a BLOB usando Base64
)
