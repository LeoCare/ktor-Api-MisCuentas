//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.dto](../index.md)/[UsuarioCrearDto](index.md)

# UsuarioCrearDto

[jvm]\
@Serializable

data class [UsuarioCrearDto](index.md)(val nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = TipoPerfil.Tipo.USER_M.name)

Usuario nuevo a crear

## Constructors

| | |
|---|---|
| [UsuarioCrearDto](-usuario-crear-dto.md) | [jvm]<br>constructor(nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = TipoPerfil.Tipo.USER_M.name) |

## Functions

| Name | Summary |
|---|---|
| [toModel](../../com.miscuentas.mappers/to-model.md) | [jvm]<br>fun [UsuarioCrearDto](index.md).[toModel](../../com.miscuentas.mappers/to-model.md)(): [Usuario](../../com.miscuentas.models/-usuario/index.md) |

## Properties

| Name | Summary |
|---|---|
| [contrasenna](contrasenna.md) | [jvm]<br>val [contrasenna](contrasenna.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [correo](correo.md) | [jvm]<br>val [correo](correo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [nombre](nombre.md) | [jvm]<br>val [nombre](nombre.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [perfil](perfil.md) | [jvm]<br>val [perfil](perfil.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
