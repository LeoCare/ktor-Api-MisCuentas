package com.miscuentas.entities


import org.jetbrains.exposed.sql.Table

/**
 * Representa la tabla `PARTICIPANTES` en la base de datos.
 *
 * @property idParticipante Columna de ID único para cada participante, con auto-incremento.
 * @property nombre Columna que almacena el nombre del participante.
 * @property correo Columna que almacena la dirección de correo electrónico del participante. Es única para cada participante.
 * @property idUsuario Columna que almacena el ID del usuario asociado al participante. Es una clave foránea que referencia a la tabla `UsuariosTable`.
 * @property idHoja Columna que almacena el ID de la hoja asociada al participante. Es una clave foránea que referencia a la tabla `HojasTable`.
 * @property primaryKey Define la clave primaria de la tabla `PARTICIPANTES` usando la columna `idParticipante`.
 */
object ParticipantesTable : Table("PARTICIPANTES") {
    val idParticipante = long("id_participante").autoIncrement()
    val nombre = varchar("nombre", 255)
    val correo = varchar("correo", 255).uniqueIndex().nullable()
    val tipo = varchar("tipo", 255)
    val idUsuario = long("id_usuario").nullable()
    val idHoja = long("id_hoja")  references HojasTable.idHoja

    /**
     * Define la clave primaria de la tabla `PARTICIPANTES` usando la columna `idParticipante`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_Participante_ID`.
     */
    override val primaryKey = PrimaryKey(idParticipante, name = "PK_Participante_ID")
}