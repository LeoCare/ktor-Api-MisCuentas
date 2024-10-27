package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table

/**
 * Representa la tabla `TIPO_BALANCE` en la base de datos.
 *
 * @property tipo Columna que almacena el código del tipo de balance (por ejemplo, "D" para deudor, "A" para acreedor).
 * @property descripcion Columna que almacena la descripción del tipo de balance.
 * @property primaryKey Define la clave primaria de la tabla `TIPO_BALANCE` usando la columna `tipo`.
 */
object TipoBalancesTable : Table("TIPO_BALANCE") {
    val tipo = varchar("tipo", 2)
    val descripcion = varchar("descripcion", 255)

    /**
     * Define la clave primaria de la tabla `TIPO_BALANCE` usando la columna `tipo`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_TipoBalance`.
     */
    override val primaryKey = PrimaryKey(tipo, name = "PK_TipoBalance")
}

/**
 * Enumeración que representa los tipos de balance disponibles.
 *
 * @property codigo Código del tipo de balance (por ejemplo, "D" para deudor, "A" para acreedor).
 */
enum class TipoBalance(val codigo: String) {
    DEUDOR("D"),
    ACREEDOR("A"),
    BALANCEADO("B");

    companion object {
        /**
         * Obtiene el tipo de balance correspondiente a un código dado.
         *
         * @param codigo Código del tipo de balance.
         * @return El tipo de balance correspondiente, o `null` si no se encuentra.
         */
        fun fromCodigo(codigo: String): TipoBalance? {
            return entries.find { it.codigo == codigo }
        }
    }
}