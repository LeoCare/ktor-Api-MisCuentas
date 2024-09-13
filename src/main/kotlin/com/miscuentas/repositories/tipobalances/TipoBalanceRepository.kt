package com.miscuentas.repositories.tipobalances

import com.miscuentas.models.TipoBalance
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `TipoBalance`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface TipoBalanceRepository: CrudRepository<TipoBalance, String> {

}
