package com.miscuentas

import com.miscuentas.config.AppConfig
import com.miscuentas.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args) //los datos para la conexion estan en -> resources/application.conf
}

fun Application.module() {
    configureMonitoring()
    configureDI()
    configureSerialization()
    configureDatabases(AppConfig())
    configureRouting()
}
