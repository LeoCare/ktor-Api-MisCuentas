package com.miscuentas.services.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.miscuentas.models.Usuario
import io.github.cdimascio.dotenv.Dotenv
import mu.KotlinLogging
import java.util.*


private val logger = KotlinLogging.logger {}

sealed class TokenException(message: String) : RuntimeException(message) {
    class InvalidTokenException(message: String) : TokenException(message)
}


class TokensService {
    val dotenv = Dotenv.configure().ignoreIfMissing().load()

    val audience by lazy { dotenv["JWT_AUDIENCE"] }
    val realm by lazy { dotenv["JWT_REALM"] }
    private val issuer by lazy { dotenv["JWT_ISSUER"] }
    private val expiresIn by lazy { dotenv["JWT_EXPIRATION"].toLong() }
    private val secret by lazy { dotenv["JWT_SECRET"] }

    init {
        logger.debug { "Servicio de token iniciado por: $audience" }
    }

    /** GENERACION DEL TOKEN **/
    fun generateJWT(usuario: Usuario): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withSubject("Authentication")
            // user claims and other data to store
            .withClaim("username", usuario.nombre)
            .withClaim("usermail", usuario.correo)
            .withClaim("userId", usuario.id_usuario.toString())
            // expiration time from currentTimeMillis + (tiempo times in seconds) * 1000 (to millis)
            .withExpiresAt(Date(System.currentTimeMillis() + expiresIn * 1000L))
            // sign with secret
            .sign(
                Algorithm.HMAC512(secret)
            )
    }


    /** VERIFICACION DEL TOKEN **/
    fun verifyJWT(): JWTVerifier {
        return try {
            JWT.require(Algorithm.HMAC512(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
        } catch (e: Exception) {
            throw TokenException.InvalidTokenException("Invalid token")
        }
    }
}