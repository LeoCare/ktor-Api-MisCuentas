//[ktor-Api-MisCuentas](../../index.md)/[com.miscuentas.dto](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [UsuarioCrearDto](-usuario-crear-dto/index.md) | [jvm]<br>@Serializable<br>data class [UsuarioCrearDto](-usuario-crear-dto/index.md)(val nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = TipoPerfil.Tipo.USER_M.name)<br>Usuario nuevo a crear |
| [UsuarioDto](-usuario-dto/index.md) | [jvm]<br>@Serializable<br>data class [UsuarioDto](-usuario-dto/index.md)(val id_usuario: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val correo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val contrasenna: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val perfil: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Usuario a devolver en las respuestas |
| [UsuarioLoginDto](-usuario-login-dto/index.md) | [jvm]<br>@Serializable<br>data class [UsuarioLoginDto](-usuario-login-dto/index.md)(val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Usuario para el logeo |
| [UsuarioPerfil](-usuario-perfil/index.md) | [jvm]<br>@Serializable<br>data class [UsuarioPerfil](-usuario-perfil/index.md)(val nombre: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val descripcion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [UsuarioWithTokenDto](-usuario-with-token-dto/index.md) | [jvm]<br>@Serializable<br>data class [UsuarioWithTokenDto](-usuario-with-token-dto/index.md)(val user: [UsuarioDto](-usuario-dto/index.md), val token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Usuario con token |
