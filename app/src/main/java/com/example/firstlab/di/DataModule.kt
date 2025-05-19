package com.example.firstlab.di

import com.example.firstlab.data.EmotionDatabase
import com.example.firstlab.data.repository.EmotionsRepositoryImpl
import com.example.firstlab.data.repository.FirebaseAuthRepositoryImpl
import com.example.firstlab.data.repository.ProfileRepositoryImpl
import com.example.firstlab.data.mapper.EmotionMapper
import com.example.firstlab.domain.repository.EmotionsRepository
import com.example.firstlab.domain.repository.FirebaseAuthRepository
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

    factoryOf(::EmotionMapper)
    factoryOf(::ProfileRepositoryImpl)
    factory<EmotionsRepository> { EmotionsRepositoryImpl(emotionDao = get(), mapper = get(), auth = get()) }
    factory<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(auth = get(), userDao = get()) }
}