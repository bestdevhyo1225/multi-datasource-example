package com.example.multidatasourceexample.common.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormatterUtils {
    private val FORMATTER_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun toStringDateTime(localDateTime: LocalDateTime): String = localDateTime.format(FORMATTER_PATTERN)
    fun toLocalDateTime(stringDateTime: String): LocalDateTime = LocalDateTime.parse(stringDateTime, FORMATTER_PATTERN)
}
