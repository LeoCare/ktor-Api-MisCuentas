//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.models](../index.md)/[Usuario](index.md)

# Usuario

[jvm]\
data class [Usuario](index.md)(val id_usuario: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

MODELO DE CLASE USUARIO:

## Constructors

| | |
|---|---|
| [Usuario](-usuario.md) | [jvm]<br>constructor(id_usuario: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Instancia un usuario unico. |

## Functions

| Name | Summary |
|---|---|
| [toDto](../../com.miscuentas.mappers/to-dto.md) | [jvm]<br>fun [Usuario](index.md).[toDto](../../com.miscuentas.mappers/to-dto.md)(): [UsuarioDto](../../com.miscuentas.dto/-usuario-dto/index.md) |

## Properties

| Name | Summary |
|---|---|
| [contrasenna](contrasenna.md) | [jvm]<br>val [contrasenna](contrasenna.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>se almacenará cifrada. |
| [correo](correo.md) | [jvm]<br>val [correo](correo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>unico para cada usuario. |
| [id_usuario](id_usuario.md) | [jvm]<br>val [id_usuario](id_usuario.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>id unico para cada usuario. |
| [nombre](nombre.md) | [jvm]<br>val [nombre](nombre.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>nombre del registro y usado para logearse. |
| [perfil](perfil.md) | [jvm]<br>val [perfil](perfil.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>restringe el acceso y las acciones.. |
