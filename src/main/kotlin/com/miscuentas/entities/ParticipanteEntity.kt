package com.miscuentas.entities


import org.jetbrains.exposed.sql.Table

object ParticipantesTable : Table("PARTICIPANTES") {
    val idParticipante = long("id_participante").autoIncrement()
    val nombre = varchar("nombre", 255)
    val correo = varchar("correo", 255).uniqueIndex()
    val idUsuario = long("id_usuario") references UsuariosTable.id_usuario
    val idHoja = long("id_hoja")  references HojasTable.idHoja

    override val primaryKey = PrimaryKey(idParticipante, name = "PK_Participante_ID")
}