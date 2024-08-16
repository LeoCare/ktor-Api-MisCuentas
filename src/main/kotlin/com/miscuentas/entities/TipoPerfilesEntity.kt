package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

/**
 * Representa la tabla `TIPO_PERFIL` en la base de datos.
 *
 * @property tipo Columna que almacena el código del tipo de perfil (por ejemplo, "ADMIN" para administradores, "USER" para usuarios).
 * @property descripcion Columna que almacena la descripción del tipo de perfil.
 * @property primaryKey Define la clave primaria de la tabla `TIPO_PERFIL` usando la columna `tipo`.
 */
object TipoPerfilesTable : Table("TIPO_PERFIL") {
    val tipo = varchar("tipo", 20)
    val descripcion = varchar("descripcion", 255)

    /**
     * Define la clave primaria de la tabla `TIPO_PERFIL` usando la columna `tipo`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_TipoPerfiles`.
     */
    override val primaryKey = PrimaryKey(tipo, name = "PK_TipoPerfiles")
}

/**
 * Enumeración que representa los tipos de perfil disponibles.
 *
 * @property codigo Código del tipo de perfil (por ejemplo, "ADMIN" para administradores, "USER" para usuarios).
 */
enum class TipoPerfil(val codigo: String) {
    ADMIN("ADMIN"),
    USER("USER");

    companion object {
        /**
         * Obtiene el tipo de perfil correspondiente a un código dado.
         *
         * @param codigo Código del tipo de perfil.
         * @return El tipo de perfil correspondiente, o `null` si no se encuentra.
         */
        fun fromCodigo(codigo: String): TipoPerfil? {
            return entries.find { it.codigo == codigo }
        }
    }
}