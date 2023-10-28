package com.mindhub.common.ext

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.ellipsis(maxSize: Int): String {
    if (maxSize >= this.length) {
        return this
    }

    return "${this.substring(0, maxSize - 1)}..."
}

fun String.dateToUnix(time: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeValues = time.split(":")
    var date = LocalDate.parse(this, formatter).atStartOfDay()

    date = date.plusHours(timeValues[0].toLong())
    date = date.plusMinutes(timeValues[1].toLong())

    return date
}