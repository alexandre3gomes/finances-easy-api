package com.finances.utils

import java.time.ZoneId
import org.springframework.security.crypto.codec.Hex
import java.time.LocalDateTime
import java.util.*

object DateUtil {

    fun convertLocalDateTimeToDate(dateTime: LocalDateTime): Date {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}