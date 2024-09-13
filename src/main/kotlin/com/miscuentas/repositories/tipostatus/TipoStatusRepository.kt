package com.miscuentas.repositories.tipostatus

import com.miscuentas.models.TipoStatus
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `TipoStatus`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface TipoStatusRepository: CrudRepository<TipoStatus, String> {
}
