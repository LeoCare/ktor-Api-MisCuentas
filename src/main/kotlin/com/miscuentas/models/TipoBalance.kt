package com.miscuentas.models

/**
 * MODELO DE CLASE TIPO BALANCE:
 * @property tipo tipo de balance (por ejemplo, 'D' para deudor, 'A' para acreedor).
 * @property descripcion descripción del tipo de balance.
 * @constructor Instancia un tipo de balance único.
 */
data class TipoBalance(
    val tipo: String,
    val descripcion: String
)