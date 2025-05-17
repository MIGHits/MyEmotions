package com.example.firstlab.di

import com.example.firstlab.domain.usecase.LoginUseCase
import com.example.firstlab.domain.usecase.GetProfileUseCase
import com.example.firstlab.domain.usecase.GetJournalDataUseCase
import com.example.firstlab.domain.usecase.GetStatisticsDataUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::LoginUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::GetJournalDataUseCase)
    factoryOf(::GetStatisticsDataUseCase)
}