//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.entities](../index.md)/[UsuarioEntity](index.md)

# UsuarioEntity

[jvm]\
data class [UsuarioEntity](index.md)(val id_usuario: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = TipoPerfil.Tipo.USER_M.name)

## Constructors

| | |
|---|---|
| [UsuarioEntity](-usuario-entity.md) | [jvm]<br>constructor(id_usuario: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = TipoPerfil.Tipo.USER_M.name) |

## Functions

| Name | Summary |
|---|---|
| [toModel](../../com.miscuentas.mappers/to-model.md) | [jvm]<br>fun [UsuarioEntity](index.md).[toModel](../../com.miscuentas.mappers/to-model.md)(): [Usuario](../../com.miscuentas.models/-usuario/index.md) |

## Properties

| Name | Summary |
|---|---|
| [contrasenna](contrasenna.md) | [jvm]<br>val [contrasenna](contrasenna.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [correo](correo.md) | [jvm]<br>val [correo](correo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id_usuario](id_usuario.md) | [jvm]<br>val [id_usuario](id_usuario.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [nombre](nombre.md) | [jvm]<br>val [nombre](nombre.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [perfil](perfil.md) | [jvm]<br>val [perfil](perfil.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
