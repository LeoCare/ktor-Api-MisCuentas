package com.miscuentas.entities

import com.miscuentas.models.TipoPerfil
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

/**
 * Representa la tabla `USUARIOS` en la base de datos.
 *
 * @property id_usuario Columna de ID único para cada usuario, con auto-incremento.
 * @property nombre Columna que almacena el nombre del usuario.
 * @property correo Columna que almacena el correo electrónico del usuario. Es único para cada usuario.
 * @property contrasenna Columna que almacena la contraseña del usuario.
 * @property perfil Columna que almacena el tipo de perfil del usuario. Es una clave foránea que referencia a la tabla `TipoPerfilesTable`.
 * @property primaryKey Define la clave primaria de la tabla `USUARIOS` usando la columna `id_usuario`.
 * @property codigo_recup_pass Codigo necesario para recuperar la contraseña.
 */
object UsuariosTable : Table("USUARIOS") {
    val id_usuario = long("id_usuario").autoIncrement()
    val nombre = varchar("nombre", 255)
    val correo = varchar("correo", 255).uniqueIndex()
    val contrasenna = varchar("contrasenna", 255)
    val perfil = varchar("perfil", 20).references(TipoPerfilesTable.tipo, ReferenceOption.CASCADE)
    val codigo_recup_pass = long("codigo_recup_pass")

    /**
     * Define la clave primaria de la tabla `USUARIOS` usando la columna `id_usuario`.
     * El nombre de la clave primaria es opcional y en este caso se denomina `PK_id_usuario`.
     */
    override val primaryKey = PrimaryKey(id_usuario, name = "PK_id_usuario") // el nombre es opcional

    /**
     * Obtiene la columna de la tabla basada en el nombre de la columna.
     *
     * @param columnName El nombre de la columna que se desea obtener.
     * @return La columna correspondiente si el nombre coincide, o `null` si no se encuentra.
     */
    fun getColumnByName(columnName: String): Column<String>? {
        return when (columnName) {
            "nombre" -> nombre
            "correo" -> correo
            "contrasenna" -> contrasenna
            "perfil" -> perfil
            else -> null
        }
    }
}
