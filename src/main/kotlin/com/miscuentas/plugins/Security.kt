package com.miscuentas.plugins

import com.miscuentas.services.auth.TokenException
import com.miscuentas.services.auth.TokensService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import mu.KotlinLogging
import org.koin.ktor.ext.inject

// Seguridad en base a JWT
fun Application.configureSecurity() {
    val logger = KotlinLogging.logger {}
    // Injectamos el Token del servicio
    val jwtService: TokensService by inject()


    authentication {
        jwt {
            // Cargo la configuracion de la verificacion
            verifier(jwtService.verifyJWT())
            // Con 'realm' recojo el token de la request.
            realm = jwtService.realm
            validate { credential ->
                val audienceValid = credential.payload.audience.contains(jwtService.audience)
                val usernameClaim = credential.payload.getClaim("username").asString()

                if (audienceValid && usernameClaim.isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    logger.warn { "Token inválido: Audiencia o 'username' no válidos." }
                    null
                }
            }

            challenge { defaultScheme, realm ->
                logger.error { "Token no válido o ha expirado." }
                throw TokenException.InvalidTokenException("El token no es valido o ha expirado!")
            }
        }
    }

}