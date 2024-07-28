package com.miscuentas.models

import org.jetbrains.exposed.sql.Table

object Imagenes : Table("IMAGENES") {
    val id = long("id_imagen").autoIncrement()
    val imagen = blob("imagen")
}