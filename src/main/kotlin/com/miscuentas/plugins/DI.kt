package com.miscuentas.plugins

import com.miscuentas.di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


/** LEVANTAMOS EL PLUGIN KOIN PARA LA DI:
    *  Para ello le pasamos los modulos creadosn en -> di/module
    *  Luego devemos llamar a esta funcion desde  -> Application.kt
 **/
fun Application.configureDI(){
    install(Koin){
        slf4jLogger()
        modules(appModule)
    }
}
