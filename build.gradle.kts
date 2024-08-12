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
// Swagger
val ktor_swagger_ui_version: String by project
// Exposed
val exposed_version: String by project
// Result Errors
val result_version: String by project
// Ksp
val koin_ksp_version: String by project


plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    // Dokka (para generar documentacion)
    id("org.jetbrains.dokka") version "1.8.10"
    // KSP for Koin Annotations
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
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
    maven("https://jitpack.io") // For Swagger UI (para generar documentacion)
}

dependencies {
    // Ktor Core
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    // Content Negotiation and Serialization
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    // Test
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    // ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    // MySql
    implementation("mysql:mysql-connector-java:$mysqlVersion")
    // Agrupa conexiones
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")
    // Monitorizacion
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    // Dependencias
    implementation("io.insert-koin:koin-ktor:$koinKtor")
    implementation("io.insert-koin:koin-core:$koinKtor")
    implementation("io.insert-koin:koin-logger-slf4j:$koinKtor")
    // Auth JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version") // Koin KSP Compiler for KSP
    // BCrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:$bcrypt_version")
    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")
    // SSL/TLS
    implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")
    // CORS (comunicacion cruzada)
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    // Swagger (para generar la documentacion)
    implementation("io.github.smiley4:ktor-swagger-ui:$ktor_swagger_ui_version")
    // Resultados en consultas segun ROP (programacion orientada a ferrocarril)
    implementation("com.michael-bull.kotlin-result:kotlin-result:$result_version")
    // Dotenv
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
}

tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.miscuentas.ApplicationKt"))
        }
    }
}

ktor {
    fatJar {
        archiveFileName.set("com.miscuentas.ktor-Api-MisCuentas-$version-all.jar")
    }
}