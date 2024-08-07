package com.miscuentas


import com.miscuentas.plugins.*
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory
import java.io.File

fun main() {
    val dotenv = Dotenv.configure().ignoreIfMissing().load()

    val keyStoreFile = File("build/keystore.jks")
    val keyStore = buildKeyStore {
        certificate(dotenv["SSL_KEY_ALIAS"]) {
            password = dotenv["SSL_KEYSTORE_PASSWORD"]
            domains = listOf("0.0.0.0", "192.168.7.3", "localhost")
        }
    }
    keyStore.saveToFile(keyStoreFile, dotenv["SSL_KEYSTORE_PASSWORD"])

    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = dotenv["PORT"].toInt()
        }
        sslConnector(
            keyStore = keyStore,
            keyAlias = dotenv["SSL_KEY_ALIAS"],
            keyStorePassword = { dotenv["SSL_KEYSTORE_PASSWORD"].toCharArray() },
            privateKeyPassword = { dotenv["SSL_PRIVATE_KEY_PASSWORD"].toCharArray() }) {
            port = dotenv["SSL_PORT"].toInt()
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }
    embeddedServer(Netty, environment).start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    configureDI()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureRouting()
    configureCors()
    configureSwagger()
}
