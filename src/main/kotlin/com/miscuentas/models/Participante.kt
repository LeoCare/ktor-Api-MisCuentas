package com.miscuentas.models

import com.miscuentas.entities.UsuariosTable
import org.jetbrains.exposed.sql.Table

object Participantes : Table("PARTICIPANTES") {
    val id = long("id_participante").autoIncrement()
    val nombre = varchar("nombre", 255)
    val correo = varchar("correo", 255).uniqueIndex()
    val idUsuario = long("id_usuario") references UsuariosTable.id_usuario
    val idHoja = long("id_hoja") references Hojas.id
}
