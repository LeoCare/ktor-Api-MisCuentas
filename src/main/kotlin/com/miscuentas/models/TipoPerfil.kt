package com.miscuentas.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class TipoPerfil(
    val tipo: String = Tipo.USER_M.name,
    val descripcion: String
){
    enum class Tipo {
        ADMIN,
        USER_M,
        USER_D
    }
}

object TipoPerfiles : Table("TIPO_PERFIL") {
    val tipo = varchar("tipo", 20)
    val descripcion = varchar("descripcion", 255)

    override val primaryKey = PrimaryKey(tipo, name = "PK_tipo")
}
