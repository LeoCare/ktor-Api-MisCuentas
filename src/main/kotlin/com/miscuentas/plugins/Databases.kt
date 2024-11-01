package com.miscuentas.plugins

import com.miscuentas.entities.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


/** HABILITA LA AGRUAPACION DE CONEXIONES:
    *Devuelve la interfaz DataSource.
    *Se usará cuando Exposed se conecte a la DDBB.
 */
private fun provideDataSource(url:String,driverClass:String): HikariDataSource {
    val hikariConfig= HikariConfig().apply {
        driverClassName=driverClass
        jdbcUrl=url
        maximumPoolSize=3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    return HikariDataSource(hikariConfig)
}

/** DEFINICIONES DEL LA DDBB:
    * Aquí definiremos la conexión a la base de datos y el esquema de la base de datos.
    * Extraemos el nombre de la clase del controlador y la URL JDBC del archivo de configuración.
    * Luego llamamos a la función Database.connect() y le pasamos la función provideDataSource() que creamos anteriormente.
    * Esto devuelve una base de datos que usamos en una función de transacción para crear las tablas indicadas, en la base de datos.
 */
fun Application.configureDatabases() {
    val dotenv = Dotenv.configure().ignoreIfMissing().load()

    val driverClass = dotenv["DB_DRIVER"]
    val jdbcUrl = dotenv["DB_URL"]
    val db = Database.connect(provideDataSource(jdbcUrl,driverClass))

    transaction(db) {
        SchemaUtils.create(
            UsuariosTable,
            TipoPerfilesTable,
            BalancesTable,
            EmailLogTable,
            GastosTable,
            HojasTable,
            ImagenesTable,
            PagosTable,
            ParticipantesTable,
            TipoBalancesTable,
            TipoStatusTable
        )
    }
}

/** FUNCION A USAR EN LAS TRANSACCIONES:
 * Esto nos asegura una comunicacion asincrono y sin bloqueos
 */
suspend fun <T> dbQuery(block:suspend ()->T):T{
    return newSuspendedTransaction(Dispatchers.IO) { block() }
}