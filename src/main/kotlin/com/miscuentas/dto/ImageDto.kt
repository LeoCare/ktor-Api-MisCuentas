package com.miscuentas.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImagenDto(
    val idImagen: Long,
    val imagen: ByteArray // O puedes usar un tipo de Base64 si prefieres
)

/** DTO para crear una nueva Imagen **/
@Serializable
data class ImagenCrearDto(
    val imagen: ByteArray
)