package com.miscuentas.di

import com.miscuentas.repositories.balances.BalanceRepository
import com.miscuentas.repositories.balances.BalanceRepositoryImpl
import com.miscuentas.repositories.emaillogs.EmailLogRepository
import com.miscuentas.repositories.emaillogs.EmailLogRepositoryImpl
import com.miscuentas.repositories.gastos.GastoRepository
import com.miscuentas.repositories.gastos.GastoRepositoryImpl
import com.miscuentas.repositories.hojas.HojaRepository
import com.miscuentas.repositories.hojas.HojaRepositoryImpl
import com.miscuentas.repositories.imagenes.ImagenRepository
import com.miscuentas.repositories.imagenes.ImagenRepositoryImpl
import com.miscuentas.repositories.pagos.PagoRepository
import com.miscuentas.repositories.pagos.PagoRepositoryImpl
import com.miscuentas.repositories.participantes.ParticipanteRepository
import com.miscuentas.repositories.participantes.ParticipanteRepositoryImpl
import com.miscuentas.repositories.tipobalances.TipoBalanceRepository
import com.miscuentas.repositories.tipobalances.TipoBalanceRepositoryImpl
import com.miscuentas.repositories.tipoperfiles.TipoPerfilRepository
import com.miscuentas.repositories.tipoperfiles.TipoPerfilRepositoryImpl
import com.miscuentas.repositories.tipostatus.TipoStatusRepository
import com.miscuentas.repositories.tipostatus.TipoStatusRepositoryImpl
import com.miscuentas.repositories.usuarios.UsuarioRepository
import com.miscuentas.repositories.usuarios.UsuarioRepositoryImpl
import com.miscuentas.services.auth.TokensService
import com.miscuentas.services.balances.BalanceService
import com.miscuentas.services.balances.BalanceServiceImp
import com.miscuentas.services.emaillogs.EmailLogService
import com.miscuentas.services.emaillogs.EmailLogServiceImp
import com.miscuentas.services.gastos.GastoService
import com.miscuentas.services.gastos.GastoServiceImp
import com.miscuentas.services.hojas.HojaService
import com.miscuentas.services.hojas.HojaServiceImp
import com.miscuentas.services.imagenes.ImagenService
import com.miscuentas.services.imagenes.ImagenServiceImp
import com.miscuentas.services.pagos.PagoService
import com.miscuentas.services.pagos.PagoServiceImp
import com.miscuentas.services.participantes.ParticipanteService
import com.miscuentas.services.participantes.ParticipanteServiceImp
import com.miscuentas.services.tipobalances.TipoBalanceService
import com.miscuentas.services.tipobalances.TipoBalanceServiceImp
import com.miscuentas.services.tipoperfiles.TipoPerfilService
import com.miscuentas.services.tipoperfiles.TipoPerfilServiceImp
import com.miscuentas.services.tipostatus.TipoStatusService
import com.miscuentas.services.tipostatus.TipoStatusServiceImp
import com.miscuentas.services.usuarios.UsuarioService
import com.miscuentas.services.usuarios.UsuarioServiceImp
import org.koin.dsl.module

/** CREAMOS LOS MODULOS PARA LA DI:
 *  Creamos la variable indicandole los modulos.
 *  Uno por cada servicio.
 **/
val appModule = module {
    //INJECCION DE SERVICIOS
    single<UsuarioRepository> { UsuarioRepositoryImpl() }
    single<UsuarioService> { UsuarioServiceImp(get()) }
    single<HojaRepository> { HojaRepositoryImpl() }
    single<HojaService> { HojaServiceImp(get()) }
    single<ParticipanteRepository> { ParticipanteRepositoryImpl() }
    single<ParticipanteService> { ParticipanteServiceImp(get()) }
    single<PagoRepository> { PagoRepositoryImpl() }
    single<PagoService> { PagoServiceImp(get()) }
    single<GastoRepository> { GastoRepositoryImpl() }
    single<GastoService> { GastoServiceImp(get()) }
    single<BalanceRepository> { BalanceRepositoryImpl() }
    single<BalanceService> { BalanceServiceImp(get()) }
    single<ImagenRepository> { ImagenRepositoryImpl() }
    single<ImagenService> { ImagenServiceImp(get()) }
    single<EmailLogRepository> { EmailLogRepositoryImpl() }
    single<EmailLogService> { EmailLogServiceImp(get()) }
    single<TipoStatusRepository> { TipoStatusRepositoryImpl() }
    single<TipoStatusService> { TipoStatusServiceImp(get()) }
    single<TipoBalanceRepository> { TipoBalanceRepositoryImpl() }
    single<TipoBalanceService> { TipoBalanceServiceImp(get()) }
    single<TipoPerfilRepository> { TipoPerfilRepositoryImpl() }
    single<TipoPerfilService> { TipoPerfilServiceImp(get()) }

    single { TokensService() }
}
