package com.miscuentas.entities

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date


object PagosTable : Table("PAGOS") {
    val idPago = long("id_pago").autoIncrement()
    val idBalance = long("id_balance") references BalancesTable.idBalance
    val idBalancePagado = long("id_balance_pagado") references BalancesTable.idBalance
    val monto = decimal("monto", 10, 2)
    val idImagen = long("id_imagen") references ImagenesTable.idImagen
    val fechaPago = date("fecha_pago")
    val fechaConfirmacion = date("fecha_confirmacion").nullable()

    override val primaryKey = PrimaryKey(idPago, name = "PK_Pago_ID")
}