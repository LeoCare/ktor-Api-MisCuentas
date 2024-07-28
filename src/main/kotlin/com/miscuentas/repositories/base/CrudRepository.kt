package com.miscuentas.repositories.base

import com.miscuentas.models.Usuario
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.Column

/** DEFINIMOS LAS OPERACIONES CRUD
 * @param T es el tipo de la entidad
 * @param ID es el tipo de nuestro ID
 */
interface CrudRepository<T, ID> {
    suspend fun getAll(): List<T>
    suspend fun getById(id: ID): T?
    suspend fun getAllBy(c: Column<String>, q: String?): List<T>
    suspend fun update(entity: T): T?
    suspend fun save(entity: T): T?
    suspend fun saveAll(entities: Iterable<T>): List<T>
    suspend fun delete(entity: T): Boolean
    suspend fun deleteAll(): Boolean
}