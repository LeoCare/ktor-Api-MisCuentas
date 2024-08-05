package com.miscuentas.config

import io.ktor.server.config.*
import org.koin.core.annotation.*

/** ENCAPSULA LA CONFIGURACION DEFINIDA EN application.conf **/
class AppConfig {
    val applicationConfiguration: ApplicationConfig = ApplicationConfig("application.conf")

    //MYSQL
    val driverClassName: String = applicationConfiguration.property("database.driverClassName").getString()
    val jdbcURL: String = applicationConfiguration.property("database.jdbcURL").getString()

    //JWT
    val secret: String = applicationConfiguration.property("jwt.secret").getString()
    val realm: String = applicationConfiguration.property("jwt.realm").getString()
    val expiration: String = applicationConfiguration.property("jwt.expiration").getString()
    val issuer: String = applicationConfiguration.property("jwt.issuer").getString()
    val audience: String = applicationConfiguration.property("jwt.audience").getString()
}