package com.miscuentas.di

import com.miscuentas.config.AppConfig
import com.miscuentas.repositories.usuarios.UsuarioRepository
import com.miscuentas.repositories.usuarios.UsuarioRepositoryImpl
import com.miscuentas.services.auth.TokensService
import com.miscuentas.services.usuarios.UsuarioService
import com.miscuentas.services.usuarios.UsuarioServiceImp
import org.koin.core.scope.get
import org.koin.dsl.module

/** CREAMOS LOS MODULOS PARA LA DI:
 *  Creamos la variable indicandole los modulos.
 *  Uno por cada servicio.
 **/
val appModule = module {
    single<UsuarioRepository> { UsuarioRepositoryImpl() }
    single<UsuarioService> { UsuarioServiceImp(get()) }
    single { AppConfig() }
    single { TokensService(get()) }
}
