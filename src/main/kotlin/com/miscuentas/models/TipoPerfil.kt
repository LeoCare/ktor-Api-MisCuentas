package com.miscuentas.models


/**
 * MODELO DE CLASE TIPO PERFIL:
 * @property tipo tipo de perfil (por ejemplo, 'ADMIN', 'USER').
 * @property descripcion descripción del tipo de perfil.
 * @constructor Instancia un tipo de perfil único.
 */
data class TipoPerfil(
    val tipo: String,
    val descripcion: String
)
