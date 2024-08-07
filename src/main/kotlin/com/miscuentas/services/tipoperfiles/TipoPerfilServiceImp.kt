package com.miscuentas.services.tipoperfiles

import com.miscuentas.dto.UsuarioPerfil
import com.miscuentas.models.TipoPerfil
import com.miscuentas.models.TipoPerfiles
import com.miscuentas.models.TipoPerfiles.descripcion
import com.miscuentas.models.TipoPerfiles.tipo
import com.miscuentas.entities.UsuariosTable
import com.miscuentas.plugins.dbQuery
import org.jetbrains.exposed.sql.*

class TipoPerfilServiceImp: TipoPerfilService {

    private fun resultRowToTipoPerfil(resultRow: ResultRow) : TipoPerfil {
        return TipoPerfil(
            tipo = resultRow[tipo],
            descripcion = resultRow[descripcion],

        )
    }
    override suspend fun addTipoPerfil(perfil: TipoPerfil): TipoPerfil? = dbQuery{
        val insertStmt = TipoPerfiles.insert {
            it[tipo] = perfil.tipo
            it[descripcion] = perfil.descripcion
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowToTipoPerfil(it) }
    }

    override suspend fun updateTipoPerfil(perfil: TipoPerfil): Boolean = dbQuery{
        TipoPerfiles.update({ tipo eq perfil.tipo  }) {
            it[descripcion] = perfil.descripcion
        } > 0
    }

    override suspend fun deleteTipoPerfil(perfil: TipoPerfil): Boolean = dbQuery {
        TipoPerfiles.deleteWhere { tipo eq perfil.tipo } > 0
    }

    override suspend fun getAllTipoPerfil(): List<TipoPerfil> = dbQuery{
        TipoPerfiles.selectAll().map { resultRowToTipoPerfil(it) }
    }

    override suspend fun getTipoPerfil(tipoPerfil: String): TipoPerfil? = dbQuery{
        TipoPerfiles.select { ( tipo eq tipoPerfil )}.map { resultRowToTipoPerfil(it) }.singleOrNull()
    }

    override suspend fun searchTipoPerfil(query: String): List<TipoPerfil> = dbQuery{
        TipoPerfiles.select { ( descripcion.lowerCase() like "%${query.lowercase()}%" )}
            .map { resultRowToTipoPerfil(it) }
    }

    override suspend fun getAllUsuarioPerfil(): List<UsuarioPerfil> = dbQuery{
        (UsuariosTable innerJoin TipoPerfiles)
            .slice(UsuariosTable.nombre, TipoPerfiles.descripcion)
            .selectAll() //si no hubiera forenKey se usa una union manual -> .select { (Users.cityId eq Cities.id) }
            .map {
                UsuarioPerfil(
                    nombre = it[UsuariosTable.nombre],
                    descripcion = it[TipoPerfiles.descripcion]
                )
            }
    }
}