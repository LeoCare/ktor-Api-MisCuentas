//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.services.tipoPerfiles](../index.md)/[TipoPerfilService](index.md)

# TipoPerfilService

interface [TipoPerfilService](index.md)

#### Inheritors

| |
|---|
| [TipoPerfilServiceImp](../-tipo-perfil-service-imp/index.md) |

## Functions

| Name | Summary |
|---|---|
| [addTipoPerfil](add-tipo-perfil.md) | [jvm]<br>abstract suspend fun [addTipoPerfil](add-tipo-perfil.md)(perfil: [TipoPerfil](../../com.miscuentas.models/-tipo-perfil/index.md)): [TipoPerfil](../../com.miscuentas.models/-tipo-perfil/index.md)? |
| [deleteTipoPerfil](delete-tipo-perfil.md) | [jvm]<br>abstract suspend fun [deleteTipoPerfil](delete-tipo-perfil.md)(perfil: [TipoPerfil](../../com.miscuentas.models/-tipo-perfil/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getAllTipoPerfil](get-all-tipo-perfil.md) | [jvm]<br>abstract suspend fun [getAllTipoPerfil](get-all-tipo-perfil.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TipoPerfil](../../com.miscuentas.models/-tipo-perfil/index.md)&gt; |
| [getAllUsuarioPerfil](get-all-usuario-perfil.md) | [jvm]<br>abstract suspend fun [getAllUsuarioPerfil](get-all-usuario-perfil.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UsuarioPerfil](../../com.miscuentas.dto/-usuario-perfil/index.md)&gt; |
| [getTipoPerfil](get-tipo-perfil.md) | [jvm]<br>abstract suspend fun [getTipoPerfil](get-tipo-perfil.md)(tipoPerfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TipoPerfil](../../com.miscuentas.models/-tipo-perfil/index.md)? |
| [searchTipoPerfil](search-tipo-perfil.md) | [jvm]<br>abstract suspend fun [searchTipoPerfil](search-tipo-perfil.md)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TipoPerfil](../../com.miscuentas.models/-tipo-perfil/index.md)&gt; |
| [updateTipoPerfil](update-tipo-perfil.md) | [jvm]<br>abstract suspend fun [updateTipoPerfil](update-tipo-perfil.md)(perfil: [TipoPerfil](../../com.miscuentas.models/-tipo-perfil/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
