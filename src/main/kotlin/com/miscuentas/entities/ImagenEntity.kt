package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

/**
 * Representa la tabla `IMAGENES` en la base de datos.
 *
 * @property idImagen Columna de ID único para cada imagen, con auto-incremento.
 * @property imagen Columna que almacena los datos de la imagen en formato binario (BLOB).
 * @property primaryKey Define la clave primaria de la tabla `IMAGENES` usando la columna `idImagen`.
 */
object ImagenesTable : Table("IMAGENES") {
    val idImagen = long("id_imagen").autoIncrement()
    val imagen = blob("imagen")

    /**
     * Define la clave primaria de la tabla `IMAGENES` usando la columna `idImagen`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Imagen_ID`.
     */
    override val primaryKey = PrimaryKey(idImagen, name = "PK_Imagen_ID")
}