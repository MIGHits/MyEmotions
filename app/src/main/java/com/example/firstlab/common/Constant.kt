package com.example.firstlab.common

import androidx.core.content.ContextCompat
import com.example.firstlab.App
import com.example.firstlab.R
import com.example.firstlab.presentation.models.BallsItem

object Constant {
    const val ARG_COMPANY_DATA = "ARG_COMPANY_LIST"
    const val ARG_PLACES_DATA = "ARG_PLACES_LIST"
    const val ARG_ACTIVITIES_DATA = "ARG_ACTIVITIES_LIST"
    const val ARG_BALLS_DATA = "ARG_BALLS_LIST"
    const val ARG_EMOTES_CATEGORY = "ARG_CATEGORY_LIST"
    const val ARG_MOOD_DATA = "ARG_MOOD_DATA"
    const val ARG_FREQUENT_DATA = "ARG_FREQUENT_DATA"
    const val ARG_NOTIFICATION_DATA = "ARG_NOTIFICATION_DATA"
    const val ARG_WEEKLY_EMOTES = "ARG_CATEGORY_LIST"
    const val ARG_NOTE_COUNT = "note_count_key"
    const val USER_ID = "user_id"
    const val ARG_WEEK_INDEX = "ARG_WEEK_INDEX"
    const val NOTIFICATION_ID = "notification_id"
    const val NOTIFICATION_TEXT = "notification_text"
    const val VIEW_PAGER_OFFSET = 70
    const val ZERO_CONST = 0
    const val PADDING_SMALL = 8
    const val PADDING_MEDIUM = 16
    const val CORNER_RADIUS = 64f
    const val GRID_SIZE = 4
    const val BALLS_SCALE_UP = 1.35f
    const val BALLS_SCALE_DOWN = 1f
    const val BASIC_ANIM_DURATION = 200L
    const val BALLS_TRANSLATION = 40f
    const val MINIMUM_CONST = -1
    const val MIN_CATEGORY_SIZE = 20
    const val STROKE_WIDTH = 60f
    const val START_ANGLE = -90f
    const val CIRCLE_ANIM_DURATION = 5000L
    const val GRADIENT_ANIM_DURATION = 10000L
    const val STRIPE_CORNER_SIZE = 80f
    const val PADDING_LARGE = 32
    const val TOTAL_GOALS = 2
    const val RELEASE_YEAR = 2025
    val BALLS = listOf(
        BallsItem(
            ContextCompat.getColor(App.app, R.color.redGradient),
            name = "Ярость",
            description = ""
        ),
        BallsItem(
            ContextCompat.getColor(App.app, R.color.redGradient),
            name = "Напряжение",
            description = ""
        ),
        BallsItem(
            ContextCompat.getColor(App.app, R.color.yellowGradient),
            name = "Возбуждение",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.yellowGradient)),
            name = "Восторг",
            description = ""
        ),
        BallsItem(
            ContextCompat.getColor(App.app, R.color.redGradient),
            name = "Зависть",
            description = ""
        ),
        BallsItem(
            ContextCompat.getColor(App.app, R.color.redGradient),
            name = "Беспокойство",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.yellowGradient)),
            name = "Уверенность",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.yellowGradient)),
            name = "Счастье",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.blueGradient)),
            name = "Выгорание",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.blueGradient)),
            name = "Усталость",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.greenGradient)),
            name = "Спокойствие",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.greenGradient)),
            name = "Удовлетворенность",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.blueGradient)),
            name = "Депрессия",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.blueGradient)),
            name = "Апатия",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.greenGradient)),
            name = "Благодарность",
            description = ""
        ),
        BallsItem(
            (ContextCompat.getColor(App.app, R.color.greenGradient)),
            name = "Защищенность",
            description = ""
        )
    )

    val COMPANY: MutableSet<String> = mutableSetOf(
        "Один",
        "Друзья",
        "Семья",
        "Коллеги",
        "Партнёр",
        "Питомцы"
    )
    val PLACES: MutableSet<String> = mutableSetOf(
        "Дом",
        "Работа",
        "Школа",
        "Транспорт",
        "Улица"
    )
    val ACTIVITIES: MutableSet<String> = mutableSetOf(
        "Приём пищи",
        "Встреча с друзьями",
        "Тренировка",
        "Хобби",
        "Отдых",
        "Поездка"
    )
}