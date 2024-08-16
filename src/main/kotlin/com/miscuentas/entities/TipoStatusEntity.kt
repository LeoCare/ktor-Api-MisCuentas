package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

/**
 * Representa la tabla `TIPO_STATUS` en la base de datos.
 *
 * @property tipo Columna que almacena el código del tipo de estado (por ejemplo, "C" para abierto, "F" para finalizado).
 * @property descripcion Columna que almacena la descripción del tipo de estado.
 * @property primaryKey Define la clave primaria de la tabla `TIPO_STATUS` usando la columna `tipo`.
 */
object TipoStatusTable : Table("TIPO_STATUS") {
    val tipo = varchar("tipo", 2)
    val descripcion = varchar("descripcion", 255)

    /**
     * Define la clave primaria de la tabla `TIPO_STATUS` usando la columna `tipo`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_TipoStatus`.
     */
    override val primaryKey = PrimaryKey(tipo, name = "PK_TipoStatus")
}

/**
 * Enumeración que representa los tipos de estado disponibles.
 *
 * @property codigo Código del tipo de estado (por ejemplo, "C" para abierto, "F" para finalizado).
 */
enum class TipoStatus(val codigo: String) {
    ABIERTO("C"),
    FINALIZADO("F"),
    BALANCEADO("B"),
    ANULADO("A");

    companion object {
        /**
         * Obtiene el tipo de estado correspondiente a un código dado.
         *
         * @param codigo Código del tipo de estado.
         * @return El tipo de estado correspondiente, o `null` si no se encuentra.
         */
        fun fromCodigo(codigo: String): TipoStatus? {
            return entries.find { it.codigo == codigo }
        }
    }
}