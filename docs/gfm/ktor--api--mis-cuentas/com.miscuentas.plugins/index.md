//[ktor-Api-MisCuentas](../../index.md)/[com.miscuentas.plugins](index.md)

# Package-level declarations

## Functions

| Name | Summary |
|---|---|
| [configureCors](configure-cors.md) | [jvm]<br>fun Application.[configureCors](configure-cors.md)()<br>Mecanisnmo de seguridad implementado en navegadores Web. Permite o restringe solicitudes de origenes cruzados |
| [configureDatabases](configure-databases.md) | [jvm]<br>fun Application.[configureDatabases](configure-databases.md)()<br>DEFINICIONES DEL LA DDBB: Aquí definiremos la conexión a la base de datos y el esquema de la base de datos. Extraemos el nombre de la clase del controlador y la URL JDBC del archivo de configuración. Luego llamamos a la función Database.connect() y le pasamos la función provideDataSource() que creamos anteriormente. Esto devuelve una base de datos que usamos en una función de transacción para crear las tablas indicadas, en la base de datos. |
| [configureDI](configure-d-i.md) | [jvm]<br>fun Application.[configureDI](configure-d-i.md)()<br>LEVANTAMOS EL PLUGIN KOIN PARA LA DI: Para ello le pasamos los modulos creadosn en -> di/module Luego devemos llamar a esta funcion desde  -> Application.kt |
| [configureMonitoring](configure-monitoring.md) | [jvm]<br>fun Application.[configureMonitoring](configure-monitoring.md)() |
| [configureRouting](configure-routing.md) | [jvm]<br>fun Application.[configureRouting](configure-routing.md)() |
| [configureSecurity](configure-security.md) | [jvm]<br>fun Application.[configureSecurity](configure-security.md)() |
| [configureSerialization](configure-serialization.md) | [jvm]<br>fun Application.[configureSerialization](configure-serialization.md)() |
| [configureSwagger](configure-swagger.md) | [jvm]<br>fun Application.[configureSwagger](configure-swagger.md)() |
| [dbQuery](db-query.md) | [jvm]<br>suspend fun &lt;[T](db-query.md)&gt; [dbQuery](db-query.md)(block: suspend () -&gt; [T](db-query.md)): [T](db-query.md)<br>FUNCION A USAR EN LAS TRANSACCIONES: Esto nos asegura una comunicacion asincrono y sin bloqueos |
