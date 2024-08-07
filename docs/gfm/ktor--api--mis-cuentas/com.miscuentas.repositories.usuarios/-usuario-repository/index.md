//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.repositories.usuarios](../index.md)/[UsuarioRepository](index.md)

# UsuarioRepository

interface [UsuarioRepository](index.md) : [CrudRepository](../../com.miscuentas.repositories.base/-crud-repository/index.md)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md), [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt; 

#### Inheritors

| |
|---|
| [UsuarioRepositoryImpl](../-usuario-repository-impl/index.md) |

## Functions

| Name | Summary |
|---|---|
| [checkCorreoExist](check-correo-exist.md) | [jvm]<br>abstract suspend fun [checkCorreoExist](check-correo-exist.md)(correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [checkUserNameAndPassword](check-user-name-and-password.md) | [jvm]<br>abstract suspend fun [checkUserNameAndPassword](check-user-name-and-password.md)(nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
| [delete](index.md#651099931%2FFunctions%2F-1216412040) | [jvm]<br>abstract suspend fun [delete](index.md#651099931%2FFunctions%2F-1216412040)(entity: [Usuario](../../com.miscuentas.models/-usuario/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [deleteAll](../../com.miscuentas.repositories.base/-crud-repository/delete-all.md) | [jvm]<br>abstract suspend fun [deleteAll](../../com.miscuentas.repositories.base/-crud-repository/delete-all.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getAll](../../com.miscuentas.repositories.base/-crud-repository/get-all.md) | [jvm]<br>abstract suspend fun [getAll](../../com.miscuentas.repositories.base/-crud-repository/get-all.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;? |
| [getAllBy](../../com.miscuentas.repositories.base/-crud-repository/get-all-by.md) | [jvm]<br>abstract suspend fun [getAllBy](../../com.miscuentas.repositories.base/-crud-repository/get-all-by.md)(c: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), q: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;? |
| [getById](index.md#765309474%2FFunctions%2F-1216412040) | [jvm]<br>abstract suspend fun [getById](index.md#765309474%2FFunctions%2F-1216412040)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
| [hashedPassword](hashed-password.md) | [jvm]<br>abstract fun [hashedPassword](hashed-password.md)(contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [save](index.md#373772781%2FFunctions%2F-1216412040) | [jvm]<br>abstract suspend fun [save](index.md#373772781%2FFunctions%2F-1216412040)(entity: [Usuario](../../com.miscuentas.models/-usuario/index.md)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
| [saveAll](index.md#2125355730%2FFunctions%2F-1216412040) | [jvm]<br>abstract suspend fun [saveAll](index.md#2125355730%2FFunctions%2F-1216412040)(entities: [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;? |
| [update](index.md#1193701305%2FFunctions%2F-1216412040) | [jvm]<br>abstract suspend fun [update](index.md#1193701305%2FFunctions%2F-1216412040)(entity: [Usuario](../../com.miscuentas.models/-usuario/index.md)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
