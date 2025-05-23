package com.example.firstlab.di

import com.example.firstlab.data.EmotionDatabase
import com.example.firstlab.data.repository.EmotionsRepositoryImpl
import com.example.firstlab.data.repository.FirebaseAuthRepositoryImpl
import com.example.firstlab.data.repository.SettingsRepositoryImpl
import com.example.firstlab.data.mapper.EmotionMapper
import com.example.firstlab.data.mapper.ProfileMapper
import com.example.firstlab.data.scheduler.NotificationSchedulerImpl
import com.example.firstlab.domain.repository.EmotionsRepository
import com.example.firstlab.domain.repository.FirebaseAuthRepository
import com.example.firstlab.domain.repository.SettingsRepository
import com.example.firstlab.domain.scheduler.NotificationScheduler
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        EmotionDatabase.getDatabase()
    }

    single {
        get<EmotionDatabase>().EmotionDao()
    }
    single {
        get<EmotionDatabase>().UserDao()
    }
    single {
        get<EmotionDatabase>().NotificationDao()
    }
    factory<NotificationScheduler> {
        NotificationSchedulerImpl(context = get(), auth = get())
    }

    factoryOf(::EmotionMapper)
    factory<SettingsRepository> {
        SettingsRepositoryImpl(
            userDao = get(),
            notificationDao = get(),
            auth = get(),
            profileMapper = get()
        )
    }
    factoryOf(::ProfileMapper)
    factory<EmotionsRepository> {
        EmotionsRepositoryImpl(
            emotionDao = get(),
            mapper = get(),
            auth = get()
        )
    }
    factory<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(auth = get(), userDao = get()) }
}