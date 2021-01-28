package com.finances.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

object DateUtil {

    fun convertLocalDateTimeToDate(dateTime: LocalDateTime): Date {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}
