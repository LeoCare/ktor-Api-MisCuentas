package com.miscuentas.mappers

import com.miscuentas.dto.EmailLogCrearDto
import com.miscuentas.dto.EmailLogDto
import com.miscuentas.entities.EmailLogTable.fechaEnvio
import com.miscuentas.models.EmailLog
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Extensión para convertir una instancia de `EmailLog` a `EmailLogDto`.
 *
 * @return Una instancia de `EmailLogDto` con los datos del `EmailLog`.
 */
fun EmailLog.toDto() = EmailLogDto(
    idEmail = this.idEmail,
    idBalance = this.idBalance,
    tipo = this.tipo,
    fechaEnvio = this.fechaEnvio?.format(dateFormatter),
    status = this.status
)

/**
 * Extensión para convertir una lista de `EmailLog` a una lista de `EmailLogDto`.
 *
 * @return Una lista de instancias `EmailLogDto` correspondientes a la lista de `EmailLog`.
 */
fun List<EmailLog>.toDto() = this.map { it.toDto() }


/**
 * Extensión para convertir una instancia de `EmailLog` a `EmailLogDto`.
 *
 * @return Una instancia de `EmailLogDto` con los datos del `EmailLog`.
 */
fun EmailLogDto.toModel() = EmailLog(
    idEmail = this.idEmail,
    idBalance = this.idBalance,
    tipo = this.tipo,
    fechaEnvio = this.fechaEnvio?.let { LocalDate.parse(it, dateFormatter) },
    status = this.status
)

/**
 * Extensión para convertir una instancia de `EmailLogCrearDto` a `EmailLog`.
 *
 * @return Una instancia de `EmailLog` con los datos del `EmailLogCrearDto`.
 */
fun EmailLogCrearDto.toModel() = EmailLog(
    idEmail = 0,
    idBalance = this.idBalance,
    tipo = this.tipo,
    fechaEnvio =  this.fechaEnvio?.let { LocalDate.parse(it, dateFormatter) },
    status = this.status
)

/**
 * Extensión para convertir una lista de `EmailLogCrearDto` a una lista de `EmailLog`.
 *
 * @return Una lista de instancias `EmailLog` correspondientes a la lista de `EmailLogCrearDto`.
 */
fun List<EmailLogCrearDto>.toModel() = this.map { it.toModel() }
