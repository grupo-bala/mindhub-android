package com.mindhub.common.ext

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
    val date = LocalDateTime.parse(this, formatter)
    val timeValues = time.split(":")

    date.plusHours(timeValues[0].toLong())
    date.plusMinutes(timeValues[1].toLong())

    return date
}