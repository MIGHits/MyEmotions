package com.example.firstlab.presentation.mapper

import com.example.firstlab.App
import com.example.firstlab.R
import com.example.firstlab.presentation.models.TimeOfDay
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
import java.util.Calendar
import java.util.Date
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
        val todayStr = App.app.getString(R.string.today, "")
        val yesterdayStr = App.app.getString(R.string.yesterday, "")

        return when {
            this.startsWith(todayStr.dropLast(1)) -> {
                val time = this.substringAfter(",").trim()
                val localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
                ZonedDateTime.of(today, localTime, zoneId).toInstant().toEpochMilli()
            }

            this.startsWith(yesterdayStr.dropLast(1)) -> {
                val time = this.substringAfter(",").trim()
                val localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
                ZonedDateTime.of(today.minusDays(1), localTime, zoneId).toInstant().toEpochMilli()
            }

            "," in this && !this.contains('.') -> {
                val (dayOfWeekStr, timeStr) = this.split(",")
                val time = LocalTime.parse(timeStr.trim(), DateTimeFormatter.ofPattern("HH:mm"))
                val dayOfWeek = DayOfWeek.entries.first {
                    it.getDisplayName(TextStyle.FULL, Locale("ru")) == dayOfWeekStr.trim()
                }
                val date = today.with(TemporalAdjusters.previousOrSame(dayOfWeek))
                ZonedDateTime.of(date, time, zoneId).toInstant().toEpochMilli()
            }

            else -> {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm")
                val parsed = LocalDateTime.parse(this, formatter)
                ZonedDateTime.of(parsed, zoneId).toInstant().toEpochMilli()
            }
        }
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}

fun Long.getTimeOfDay(): TimeOfDay {
    val hour =
        Calendar.getInstance().apply { timeInMillis = this@getTimeOfDay }.get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..7 -> TimeOfDay.EARLY_MORNING
        in 8..11 -> TimeOfDay.MORNING
        in 12..16 -> TimeOfDay.DAY
        in 17..20 -> TimeOfDay.EVENING
        else -> TimeOfDay.LATE_EVENING
    }
}

fun Date.truncateToDay(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}
