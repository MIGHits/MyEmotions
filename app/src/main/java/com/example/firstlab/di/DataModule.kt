package com.example.firstlab.di

import com.example.firstlab.data.EmotionDatabase
import com.example.firstlab.data.repository.ProfileRepositoryImpl
import com.example.firstlab.data.repository.EmotionsRepositoryImpl
import com.example.firstlab.data.repository.FirebaseAuthRepositoryImpl
import com.example.firstlab.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
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


    factoryOf(::ProfileRepositoryImpl)
    factoryOf(::EmotionsRepositoryImpl)
    factory<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(auth = get(), userDao = get()) }
}