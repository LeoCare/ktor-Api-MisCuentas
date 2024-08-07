//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.repositories.base](../index.md)/[CrudRepository](index.md)

# CrudRepository

interface [CrudRepository](index.md)&lt;[T](index.md), [ID](index.md)&gt;

DEFINIMOS LAS OPERACIONES CRUD

#### Parameters

jvm

| | |
|---|---|
| T | es el tipo de la entidad |
| ID | es el tipo de nuestro ID |

#### Inheritors

| |
|---|
| [UsuarioRepository](../../com.miscuentas.repositories.usuarios/-usuario-repository/index.md) |

## Functions

| Name | Summary |
|---|---|
| [delete](delete.md) | [jvm]<br>abstract suspend fun [delete](delete.md)(entity: [T](index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [deleteAll](delete-all.md) | [jvm]<br>abstract suspend fun [deleteAll](delete-all.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getAll](get-all.md) | [jvm]<br>abstract suspend fun [getAll](get-all.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt;? |
| [getAllBy](get-all-by.md) | [jvm]<br>abstract suspend fun [getAllBy](get-all-by.md)(c: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), q: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt;? |
| [getById](get-by-id.md) | [jvm]<br>abstract suspend fun [getById](get-by-id.md)(id: [ID](index.md)): [T](index.md)? |
| [save](save.md) | [jvm]<br>abstract suspend fun [save](save.md)(entity: [T](index.md)): [T](index.md)? |
| [saveAll](save-all.md) | [jvm]<br>abstract suspend fun [saveAll](save-all.md)(entities: [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[T](index.md)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt;? |
| [update](update.md) | [jvm]<br>abstract suspend fun [update](update.md)(entity: [T](index.md)): [T](index.md)? |
