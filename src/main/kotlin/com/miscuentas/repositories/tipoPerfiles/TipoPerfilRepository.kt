package com.miscuentas.repositories.tipoPerfiles

import com.miscuentas.models.TipoPerfil
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `TipoPerfil`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface TipoPerfilRepository: CrudRepository<TipoPerfil, String> {

}
