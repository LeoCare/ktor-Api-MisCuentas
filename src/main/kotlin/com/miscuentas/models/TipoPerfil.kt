package com.miscuentas.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class TipoPerfil(
    val tipo: String,
    val descripcion: String
)

object TipoPerfiles : Table("TIPO_PERFIL") {
    val tipo = varchar("tipo", 2)
    val descripcion = varchar("descripcion", 255)

    override val primaryKey = PrimaryKey(tipo, name = "PK_tipo")
}
