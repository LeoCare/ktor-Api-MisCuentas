package com.miscuentas

//import io.ktor.server.engine.*
import com.miscuentas.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*


fun main(args: Array<String>): Unit {
    EngineMain.main(args) //los datos para la conexion estan en -> resources/application.conf
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
