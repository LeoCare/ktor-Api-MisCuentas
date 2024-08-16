package com.miscuentas.dto

import kotlinx.serialization.Serializable

/** Serialización de una imagen general:
 * @property idImagen id único para cada imagen.
 * @property imagen datos de la imagen en formato binario o Base64.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class ImagenDto(
    val idImagen: Long,
    val imagen: String  //ByteArray O puedes usar un tipo de Base64 si prefieres
)

/** Serialización de una imagen nueva a crear:
 * @property imagen datos de la imagen en formato binario o Base64.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class ImagenCrearDto(
    val imagen: String
)

/** Serialización de una imagen a actualizar:
 * @property idImagen id único de la imagen a actualizar.
 * @property imagen datos de la imagen en formato binario o Base64.
 * @constructor Instancia a serializar.
 * */
@Serializable
data class ImagenUpdateDto (
    val idImagen: Long,
    val imagen: String // Datos de la imagen en formato Base64 para actualizar la imagen existente
)
