package com.miscuentas.repositories.base

/**
 * Interfaz que define las operaciones CRUD básicas.
 *
 * @param T El tipo de la entidad.
 * @param ID El tipo del ID de la entidad.
 */
interface CrudRepository<T, ID> {

    /**
     * Obtiene todas las entidades del tipo `T`.
     *
     * @return Una lista de todas las entidades, o `null` si no se encuentran.
     */
    suspend fun getAll(): List<T>?

    /**
     * Obtiene una entidad del tipo `T` por su ID.
     *
     * @param id El ID de la entidad a buscar.
     * @return La entidad que coincide con el ID especificado, o `null` si no se encuentra.
     */
    suspend fun getById(id: ID): T?

    /**
     * Obtiene una lista de entidades del tipo `T` que coinciden con una columna y una consulta.
     *
     * @param c El nombre de la columna por la cual se realiza la búsqueda.
     * @param q El valor de la consulta para buscar en la columna especificada.
     * @return Una lista de entidades que coinciden con la consulta, o `null` si no se encuentran.
     */
    suspend fun getAllBy(c: String, q: String?): List<T>?

    /**
     * Actualiza una entidad existente del tipo `T`.
     *
     * @param entity La entidad a actualizar.
     * @return La entidad actualizada, o `null` si la actualización falla.
     */
    suspend fun update(entity: T): T?

    /**
     * Guarda una nueva entidad del tipo `T`.
     *
     * @param entity La entidad a guardar.
     * @return La entidad guardada, o `null` si el guardado falla.
     */
    suspend fun save(entity: T): T?

    /**
     * Guarda una lista de entidades del tipo `T`.
     *
     * @param entities Las entidades a guardar.
     * @return Una lista de entidades guardadas, o `null` si el guardado falla.
     */
    suspend fun saveAll(entities: Iterable<T>): List<T>?

    /**
     * Elimina una entidad existente del tipo `T`.
     *
     * @param entity La entidad a eliminar.
     * @return `true` si la entidad fue eliminada con éxito, `false` si la eliminación falla.
     */
    suspend fun delete(entity: T): Boolean

    /**
     * Elimina todas las entidades del tipo `T`.
     *
     * @return `true` si todas las entidades fueron eliminadas con éxito, `false` si la eliminación falla.
     */
    suspend fun deleteAll(): Boolean
}