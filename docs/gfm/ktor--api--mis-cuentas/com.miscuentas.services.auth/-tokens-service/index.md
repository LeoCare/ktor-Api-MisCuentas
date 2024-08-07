//[ktor-Api-MisCuentas](../../../index.md)/[com.miscuentas.services.auth](../index.md)/[TokensService](index.md)

# TokensService

[jvm]\
class [TokensService](index.md)

## Constructors

| | |
|---|---|
| [TokensService](-tokens-service.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [generateJWT](generate-j-w-t.md) | [jvm]<br>fun [generateJWT](generate-j-w-t.md)(usuario: [Usuario](../../com.miscuentas.models/-usuario/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>GENERACION DEL TOKEN |
| [verifyJWT](verify-j-w-t.md) | [jvm]<br>fun [verifyJWT](verify-j-w-t.md)(): JWTVerifier<br>VERIFICACION DEL TOKEN |

## Properties

| Name | Summary |
|---|---|
| [audience](audience.md) | [jvm]<br>val [audience](audience.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [dotenv](dotenv.md) | [jvm]<br>val [dotenv](dotenv.md): Dotenv |
| [realm](realm.md) | [jvm]<br>val [realm](realm.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
