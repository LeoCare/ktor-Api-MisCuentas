package com.miscuentas.models

/**
 * MODELO DE CLASE TIPO STATUS:
 * @property tipo tipo de estado (por ejemplo, 'AB' para abierto, 'CL' para cerrado).
 * @property descripcion descripción del tipo de estado.
 * @constructor Instancia un tipo de estado único.
 */
data class TipoStatus(
    val tipo: String,
    val descripcion: String
)