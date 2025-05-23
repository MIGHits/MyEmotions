package com.example.firstlab.presentation.mapper

import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.presentation.models.NotificationPresentationModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class NotificationMapper {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val secondFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("H:mm")

    private fun parseTime(time: String): LocalTime {
        for (formatter in listOf(formatter, secondFormatter)) {
            try {
                return LocalTime.parse(time, formatter)
            } catch (_: DateTimeParseException) {
            }
        }
        throw IllegalArgumentException()
    }

    fun mapToDomain(notification: NotificationPresentationModel): NotificationEntity {
        return NotificationEntity(
            id = notification.id,
            time = parseTime(notification.time)
        )
    }

    private fun mapToPresentation(notification: NotificationEntity): NotificationPresentationModel {
        return NotificationPresentationModel(
            id = notification.id,
            time = notification.time.format(formatter)
        )
    }

    fun mapToPresentation(notifications: List<NotificationEntity>): List<NotificationPresentationModel> {
        return notifications.map { mapToPresentation(it) }
    }
}