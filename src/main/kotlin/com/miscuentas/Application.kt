package com.miscuentas

import com.miscuentas.config.AppConfig
import com.miscuentas.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main(args: Array<String>) {
    val dotenv = Dotenv.load()

    val keyStorePath = dotenv["SSL_KEYSTORE_PATH"]
    val keyAlias = dotenv["SSL_KEY_ALIAS"]
    val keyStorePassword = dotenv["SSL_KEYSTORE_PASSWORD"]
    val privateKeyPassword = dotenv["SSL_PRIVATE_KEY_PASSWORD"]


    //EngineMain.main(args) //los datos para la conexion estan en -> resources/application.conf

    embeddedServer(Netty, port = 8080, sslPort = 8443) {
        sslConnector(
            keyStore = File(keyStorePath).inputStream(),
            keyAlias = keyAlias,
            keyStorePassword = { keyStorePassword.toCharArray() },
            privateKeyPassword = { privateKeyPassword.toCharArray() }
        ) {
            // Configuración de SSL/TLS segura
            enableSessionCreation = true
            sessionCacheSize = 0
            sessionTimeout = 86400
            protocol = "TLS"
            protocols = arrayOf("TLSv1.2", "TLSv1.3")
            cipherSuites = arrayOf(
                "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
                "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
                "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
                "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA"
            )
        }
        module(Application::module)
    }.start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureDI()
    configureSerialization()
    configureDatabases(AppConfig())
    configureRouting()
    configureCors()
    configureSwagger()
}
