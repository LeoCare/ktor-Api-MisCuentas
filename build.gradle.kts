val kotlin_version: String by project
val ktor_version: String by project
val logback_version: String by project
// MySql
val mysqlVersion:String by project
// Agrupa conexiones
val hikaricpVersion: String by project
// DI
val koinKtor: String by project
// BCrypt
val bcrypt_version: String by project
// Logger
val micrologging_version: String by project
val logbackclassic_version: String by project




plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "com.miscuentas"
version = "0.0.1"

application {
    mainClass.set("com.miscuentas.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // ORM
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-dao:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")
    implementation("org.jetbrains.exposed:exposed-java-time:0.37.3")
    // MySql
    implementation("mysql:mysql-connector-java:$mysqlVersion")
    // Agrupa conexiones
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")
    // Monitorizacion
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    // Dependencias
    implementation("io.insert-koin:koin-ktor:$koinKtor")
    // Auth JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    // BCrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:$bcrypt_version")
    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackclassic_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")
}
