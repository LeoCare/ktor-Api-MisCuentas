package com.miscuentas.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.AuthScheme
import io.github.smiley4.ktorswaggerui.dsl.AuthType
import io.ktor.server.application.*

fun Application.configureSwagger() {
    // https://github.com/SMILEY4/ktor-swagger-ui/wiki/Configuration
    // http://xxx/swagger/
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }
        info {
            title = "API REST - MIS CUENTAS -"
            version = "latest"
            description = "API REST en Ktor para proyecto DAM"
            contact {
                name = "Leonardo David Care Prado"
                url = "https://github.com/LeoCare"
            }
            license {
                name = "Creative Commons Attribution-ShareAlike 4.0 International License"
                url = "https://LeoCare.dev/docs/license/"
            }
        }

        schemasInComponentSection = true
        examplesInComponentSection = true
        automaticTagGenerator = { url -> url.firstOrNull() }
        // We can filter paths and methods
        pathFilter = { method, url ->
            url.contains("usuarios")
            //(method == HttpMethod.Get && url.firstOrNull() == "api")
            // || url.contains("test")
        }

        // We can add security
        securityScheme("JWT-Auth") {
            type = AuthType.HTTP
            scheme = AuthScheme.BEARER
            bearerFormat = "jwt"
        }
    }
}