package com.miscuentas.entities


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

/**
 * Representa la tabla `GASTOS` en la base de datos.
 *
 * @property idGasto Columna de ID único para cada gasto, con auto-incremento.
 * @property tipo Columna que almacena el tipo de gasto (por ejemplo, "alimentación", "transporte").
 * @property concepto Columna que almacena la descripción o concepto del gasto.
 * @property importe Columna que almacena el monto del gasto.
 * @property fechaGasto Columna que almacena la fecha en la que se realizó el gasto.
 * @property idParticipante Columna que almacena el ID del participante asociado al gasto. Es una clave foránea que referencia a la tabla `ParticipantesTable`.
 * @property idImagen Columna que almacena el ID de la imagen asociada al gasto (si aplica). Es una clave foránea que referencia a la tabla `ImagenesTable`.
 * @property primaryKey Define la clave primaria de la tabla `GASTOS` usando la columna `idGasto`.
 */
object GastosTable : Table("GASTOS") {
    val idGasto = long("id_gasto").autoIncrement()
    val tipo = varchar("tipo", 255)
    val concepto = text("concepto")
    val importe = decimal("importe", 10, 2)
    val fechaGasto = date("fecha_gasto")
    val idParticipante = long("id_participante") references ParticipantesTable.idParticipante
    val idImagen = long("id_imagen").nullable()

    /**
     * Define la clave primaria de la tabla `GASTOS` usando la columna `idGasto`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Gasto_ID`.
     */
    override val primaryKey = PrimaryKey(idGasto, name = "PK_Gasto_ID")
}