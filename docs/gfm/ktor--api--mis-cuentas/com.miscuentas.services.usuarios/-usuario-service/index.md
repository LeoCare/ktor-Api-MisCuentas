//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.services.usuarios](../index.md)/[UsuarioService](index.md)

# UsuarioService

interface [UsuarioService](index.md)

#### Inheritors

| |
|---|
| [UsuarioServiceImp](../-usuario-service-imp/index.md) |

## Functions

| Name | Summary |
|---|---|
| [addUsuario](add-usuario.md) | [jvm]<br>abstract suspend fun [addUsuario](add-usuario.md)(usuario: [Usuario](../../com.miscuentas.models/-usuario/index.md)): Result&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md), [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [checkCorreoExist](check-correo-exist.md) | [jvm]<br>abstract suspend fun [checkCorreoExist](check-correo-exist.md)(correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Result&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [checkUserNameAndPassword](check-user-name-and-password.md) | [jvm]<br>abstract suspend fun [checkUserNameAndPassword](check-user-name-and-password.md)(nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Result&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md), [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [deleteUsuario](delete-usuario.md) | [jvm]<br>abstract suspend fun [deleteUsuario](delete-usuario.md)(usuario: [Usuario](../../com.miscuentas.models/-usuario/index.md)): Result&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [getAllUsuarios](get-all-usuarios.md) | [jvm]<br>abstract suspend fun [getAllUsuarios](get-all-usuarios.md)(): Result&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;, [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [getUsuarioById](get-usuario-by-id.md) | [jvm]<br>abstract suspend fun [getUsuarioById](get-usuario-by-id.md)(idUsuario: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): Result&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md), [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [getUsuariosBy](get-usuarios-by.md) | [jvm]<br>abstract suspend fun [getUsuariosBy](get-usuarios-by.md)(column: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Result&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;, [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [isAdmin](is-admin.md) | [jvm]<br>abstract suspend fun [isAdmin](is-admin.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): Result&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [saveAllUsuarios](save-all-usuarios.md) | [jvm]<br>abstract suspend fun [saveAllUsuarios](save-all-usuarios.md)(usuarios: [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;): Result&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md)&gt;, [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
| [updateUsuario](update-usuario.md) | [jvm]<br>abstract suspend fun [updateUsuario](update-usuario.md)(usuario: [Usuario](../../com.miscuentas.models/-usuario/index.md)): Result&lt;[Usuario](../../com.miscuentas.models/-usuario/index.md), [UsuarioErrores](../../com.miscuentas.errors/-usuario-errores/index.md)&gt; |
