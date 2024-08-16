package com.miscuentas.models

import org.jetbrains.exposed.sql.statements.api.ExposedBlob

/**
 * MODELO DE CLASE IMAGEN:
 * @property idImagen id único para cada imagen.
 * @property imagen datos de la imagen en formato de bytes.
 * @constructor Instancia una imagen única.
 */
data class Imagen(
    val idImagen: Long,
    val imagen: ExposedBlob
)