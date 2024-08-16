package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

/**
 * Representa la tabla `PAGOS` en la base de datos.
 *
 * @property idPago Columna de ID único para cada pago, con auto-incremento.
 * @property idBalance Columna que almacena el ID del balance asociado al pago. Es una clave foránea que referencia a la tabla `BalancesTable`.
 * @property idBalancePagado Columna que almacena el ID del balance que ha sido pagado (si aplica). Es una clave foránea que referencia a la tabla `BalancesTable`.
 * @property monto Columna que almacena el monto del pago.
 * @property idImagen Columna que almacena el ID de la imagen asociada al pago (si aplica). Es una clave foránea que referencia a la tabla `ImagenesTable`.
 * @property fechaPago Columna que almacena la fecha en la que se realizó el pago.
 * @property fechaConfirmacion Columna que almacena la fecha en la que se confirmó el pago (puede ser nulo si el pago aún no ha sido confirmado).
 * @property primaryKey Define la clave primaria de la tabla `PAGOS` usando la columna `idPago`.
 */
object PagosTable : Table("PAGOS") {
    val idPago = long("id_pago").autoIncrement()
    val idBalance = long("id_balance") references BalancesTable.idBalance
    val idBalancePagado = long("id_balance_pagado") references BalancesTable.idBalance
    val monto = decimal("monto", 10, 2)
    val idImagen = long("id_imagen") references ImagenesTable.idImagen
    val fechaPago = date("fecha_pago")
    val fechaConfirmacion = date("fecha_confirmacion").nullable()

    /**
     * Define la clave primaria de la tabla `PAGOS` usando la columna `idPago`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Pago_ID`.
     */
    override val primaryKey = PrimaryKey(idPago, name = "PK_Pago_ID")
}