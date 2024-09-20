package com.miscuentas.models

/**
 * MODELO DE CLASE PARTICIPANTE:
 * @property idParticipante id único para cada participante.
 * @property nombre nombre del participante.
 * @property correo correo electrónico único para cada participante.
 * @property idUsuario referencia al id del usuario asociado.
 * @property idHoja referencia al id de la hoja asociada.
 * @constructor Instancia un participante único.
 */
data class Participante(
    val idParticipante: Long,
    val nombre: String,
    val correo: String? = null,
    val idUsuario: Long? = null,
    val idHoja: Long
)
