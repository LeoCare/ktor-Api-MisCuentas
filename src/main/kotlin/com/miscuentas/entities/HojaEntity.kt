package com.miscuentas.entities

import com.miscuentas.models.TipoStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

/**
 * Representa la tabla `HOJAS` en la base de datos.
 *
 * @property idHoja Columna de ID único para cada hoja, con auto-incremento.
 * @property titulo Columna que almacena el título de la hoja.
 * @property fechaCreacion Columna que almacena la fecha en la que se creó la hoja.
 * @property fechaCierre Columna que almacena la fecha en la que se cerró la hoja (puede ser nulo si la hoja no está cerrada).
 * @property limiteGastos Columna que almacena el límite de gastos para la hoja.
 * @property status Columna que almacena el estado de la hoja (por ejemplo, "abierta" o "cerrada"). Es una clave foránea que referencia a la tabla `TipoStatusTable`.
 * @property idUsuario Columna que almacena el ID del usuario asociado a la hoja. Es una clave foránea que referencia a la tabla `UsuariosTable`.
 * @property primaryKey Define la clave primaria de la tabla `HOJAS` usando la columna `idHoja`.
 */
object HojasTable : Table("HOJAS") {
    val idHoja = long("id_hoja").autoIncrement()
    val titulo = varchar("titulo", 255)
    val fechaCreacion = date("fecha_creacion")
    val fechaCierre = date("fecha_cierre").nullable()
    val limiteGastos = decimal("limite_gastos", 10, 2)
    val status = varchar("status", 2) references TipoStatusTable.tipo
    val idUsuario = long("id_usuario") references UsuariosTable.id_usuario

    /**
     * Define la clave primaria de la tabla `HOJAS` usando la columna `idHoja`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Hoja_ID`.
     */
    override val primaryKey = PrimaryKey(idHoja, name = "PK_Hoja_ID")
}