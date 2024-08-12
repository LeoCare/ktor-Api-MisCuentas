package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

object ImagenesTable : Table("IMAGENES") {
    val idImagen = long("id_imagen").autoIncrement()
    val imagen = blob("imagen")

    override val primaryKey = PrimaryKey(idImagen, name = "PK_Imagen_ID")
}