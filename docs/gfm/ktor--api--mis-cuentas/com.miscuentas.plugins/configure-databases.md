//[ktor-Api-MisCuentas](../../index.md)/[com.miscuentas.plugins](index.md)/[configureDatabases](configure-databases.md)

# configureDatabases

[jvm]\
fun Application.[configureDatabases](configure-databases.md)()

DEFINICIONES DEL LA DDBB: Aquí definiremos la conexión a la base de datos y el esquema de la base de datos. Extraemos el nombre de la clase del controlador y la URL JDBC del archivo de configuración. Luego llamamos a la función Database.connect() y le pasamos la función provideDataSource() que creamos anteriormente. Esto devuelve una base de datos que usamos en una función de transacción para crear las tablas indicadas, en la base de datos.
