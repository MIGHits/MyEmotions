package com.example.firstlab.presentation.mapper

import com.example.firstlab.App
import com.example.firstlab.R
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.Locale


fun Long.isToday(): Boolean {
    val date = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val today = LocalDate.now(ZoneId.systemDefault())
    return date == today
}

fun Long.convertTime(): String {
    val zoneId = ZoneId.systemDefault()
    val zonedDateTime = Instant.ofEpochMilli(this).atZone(zoneId)

    val today = LocalDate.now(zoneId)

    when (zonedDateTime.toLocalDate()) {
        today -> return App.app.getString(
            R.string.today,
            zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        )

        today.minusDays(1) -> return App.app.getString(
            R.string.yesterday,
            zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        )
    }

    val weekStart = today.minusDays(today.dayOfWeek.value.toLong() - 1)
    val weekEnd = weekStart.plusDays(6)

    return if (zonedDateTime.toLocalDate() in weekStart..weekEnd) {
        val dayOfWeek = zonedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ru"))
        val time = zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        "$dayOfWeek,$time"
    } else {
        zonedDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm"))
    }
}

fun String.parseTimeToMillis(): Long {
    val zoneId = ZoneId.systemDefault()
    val today = LocalDate.now(zoneId)

    return try {
        if (this.startsWith(R.string.today.toChar()) || this.startsWith(R.string.yesterday.toChar())) {
            val parts = this.split(" ")
            val time = LocalTime.parse(parts.last(), DateTimeFormatter.ofPattern("HH:mm"))

            val date = when {
                this.startsWith(R.string.today.toChar()) -> today
                this.startsWith(R.string.yesterday.toChar()) -> today.minusDays(1)
                else -> today
            }

            ZonedDateTime.of(date, time, zoneId).toInstant().toEpochMilli()
        } else if (this.contains(",") && !this.contains(".")) {
            val (dayPart, timePart) = this.split(",")
            val time = LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HH:mm"))

            val dayOfWeek = DayOfWeek.entries.first {
                it.getDisplayName(TextStyle.FULL, Locale("ru")) == dayPart
            }

            val date = today.with(TemporalAdjusters.previousOrSame(dayOfWeek))
            ZonedDateTime.of(date, time, zoneId).toInstant().toEpochMilli()
        } else {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm")
            val parsed = LocalDateTime.parse(this, formatter)
            ZonedDateTime.of(parsed, zoneId).toInstant().toEpochMilli()
        }
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}
