package com.miscuentas.repositories.tipoBalances

import com.miscuentas.models.TipoBalance
import com.miscuentas.repositories.base.CrudRepository

/**
 * Repositorio para la entidad `TipoBalance`.
 * Extiende las operaciones CRUD básicas de `CrudRepository`.
 */
interface TipoBalanceRepository: CrudRepository<TipoBalance, String> {

}
