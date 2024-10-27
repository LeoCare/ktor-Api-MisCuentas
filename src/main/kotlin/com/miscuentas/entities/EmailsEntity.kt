package com.miscuentas.entities

import com.miscuentas.entities.PagosTable.nullable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.timestamp

/**
 * Representa la tabla EMAIL_LOG en la base de datos.
 *
 * @property idEmail ID único para cada registro de email, con auto-incremento.
 * @property idBalance ID del balance asociado al email, clave foránea que referencia a BalancesTable.
 * @property tipo Tipo de email ('E' para envío o 'S' para solicitud), clave foránea que referencia a TipoEmailTable.
 * @property fechaEnvio Fecha y hora en que se envió el email, con valor predeterminado de la marca de tiempo actual.
 * @property status Estado del email ('pendiente', 'enviado', 'fallido').
 * @property primaryKey Define la clave primaria de la tabla EMAIL_LOG usando la columna idEmail.
 */
object EmailLogTable : Table("EMAIL_LOG") {
    val idEmail = long("id_email").autoIncrement()
    val idBalance = long("id_balance")
        .references(BalancesTable.idBalance, onDelete = ReferenceOption.CASCADE)
    val tipo = varchar("tipo", 2)
    val fechaEnvio = date("fecha_envio").nullable()
    val status = varchar("status", 50)

    /**
     * Define la clave primaria de la tabla `EMAILS` usando la columna `idEmail`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Email_ID`.
     */
    override val primaryKey = PrimaryKey(idEmail, name = "PK_Email_ID")
}