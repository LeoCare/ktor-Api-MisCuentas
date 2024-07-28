package com.miscuentas.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Pagos : Table("PAGOS") {
    val id = long("id_pago").autoIncrement()
    val idBalance = long("id_balance") references Balances.id
    val idBalancePagado = long("id_balance_pagado") references Balances.id
    val monto = decimal("monto", 10, 2)
    val idImagen = long("id_imagen") references Imagenes.id
    val fechaPago = date("fecha_pago")
    val fechaConfirmacion = date("fecha_confirmacion").nullable()
}