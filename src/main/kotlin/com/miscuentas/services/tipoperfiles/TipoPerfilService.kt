package com.miscuentas.services.tipoperfiles

import com.miscuentas.dto.UsuarioPerfil
import com.miscuentas.models.TipoPerfil

interface TipoPerfilService {
    suspend fun addTipoPerfil(perfil: TipoPerfil): TipoPerfil?
    suspend fun updateTipoPerfil(perfil: TipoPerfil): Boolean
    suspend fun deleteTipoPerfil(perfil: TipoPerfil): Boolean
    suspend fun getAllTipoPerfil(): List<TipoPerfil>
    suspend fun getTipoPerfil(tipoPerfil: String): TipoPerfil?
    suspend fun searchTipoPerfil(query: String): List<TipoPerfil>
    suspend fun getAllUsuarioPerfil(): List<UsuarioPerfil>
}

