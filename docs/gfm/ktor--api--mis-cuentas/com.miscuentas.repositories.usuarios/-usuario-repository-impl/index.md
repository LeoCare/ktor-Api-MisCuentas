//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.repositories.usuarios](../index.md)/[UsuarioRepositoryImpl](index.md)

# UsuarioRepositoryImpl

[jvm]\
class [UsuarioRepositoryImpl](index.md) : [UsuarioRepository](../-usuario-repository/index.md)

IMPLEMENTACION DEL REPOSITORIO PARA LA CLASE USUARIO: Hereda de UsuarioRepository

## Constructors

| | |
|---|---|
| [UsuarioRepositoryImpl](-usuario-repository-impl.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [checkCorreoExist](check-correo-exist.md) | [jvm]<br>open suspend override fun [checkCorreoExist](check-correo-exist.md)(correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [checkUserNameAndPassword](check-user-name-and-password.md) | [jvm]<br>open suspend override fun [checkUserNameAndPassword](check-user-name-and-password.md)(nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
| [delete](delete.md) | [jvm]<br>open suspend override fun [delete](delete.md)(entity: [Usuario](../../com.miscuentas.models/-usuario/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [deleteAll](delete-all.md) | [jvm]<br>open suspend override fun [deleteAll](delete-all.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getAll](get-all.md) | [jvm]<br>open suspend override fun [getAll](get-all.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt; |
| [getAllBy](get-all-by.md) | [jvm]<br>open suspend override fun [getAllBy](get-all-by.md)(c: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), q: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt; |
| [getById](get-by-id.md) | [jvm]<br>open suspend override fun [getById](get-by-id.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
| [hashedPassword](hashed-password.md) | [jvm]<br>open override fun [hashedPassword](hashed-password.md)(contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>hashedPassword() codifica la contraseña antes de guardarla en persistencia. |
| [save](save.md) | [jvm]<br>open suspend override fun [save](save.md)(entity: [Usuario](../../com.miscuentas.models/-usuario/index.md)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
| [saveAll](save-all.md) | [jvm]<br>open suspend override fun [saveAll](save-all.md)(entities: [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;? |
| [update](update.md) | [jvm]<br>open suspend override fun [update](update.md)(entity: [Usuario](../../com.miscuentas.models/-usuario/index.md)): [Usuario](../../com.miscuentas.models/-usuario/index.md)? |
