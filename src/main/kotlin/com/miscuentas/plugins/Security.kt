package com.miscuentas.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.miscuentas.services.auth.TokenException
import com.miscuentas.services.auth.TokensService
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

// Seguridad en base a JWT
fun Application.configureSecurity() {

    // Injectamos el Token del servicio
    val jwtService: TokensService by inject()


    authentication {
        jwt {
            // Cargo la configuracion de la verificacion
            verifier(jwtService.verifyJWT())
            // Con 'realm' recojo el token de la request.
            realm = jwtService.realm
            validate { credential ->
                // Si el token es valido, si la audiencia es la indicada..
                // y si tiene el campo del usuario para compararlo..
                // devuelve el JWTPrincipal, si no, devuelve null
                if (credential.payload.audience.contains(jwtService.audience) &&
                    credential.payload.getClaim("username").asString().isNotEmpty()
                )
                    JWTPrincipal(credential.payload)
                else null
            }

            challenge { defaultScheme, realm ->
                throw TokenException.InvalidTokenException("El token no es valido o ha expirado!")
            }
        }
    }

}