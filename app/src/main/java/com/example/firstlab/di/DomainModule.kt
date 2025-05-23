package com.example.firstlab.di

import com.example.firstlab.domain.usecase.LoginUseCase
import com.example.firstlab.domain.usecase.GetJournalDataUseCase
import com.example.firstlab.domain.usecase.GetStatisticsDataUseCase
import com.example.firstlab.domain.usecase.CreateEmotionUseCase
import com.example.firstlab.domain.usecase.GetEmotionByIdUseCase
import com.example.firstlab.domain.usecase.GetProfileDataUseCase
import com.example.firstlab.domain.usecase.CreateNotificationUseCase
import com.example.firstlab.domain.usecase.RemoveNotificationUseCase
import com.example.firstlab.domain.usecase.UpdateUserUseCase
import com.example.firstlab.domain.usecase.ScheduleNotificationsUseCase
import com.example.firstlab.domain.usecase.CancelNotificationsUseCase
import com.example.firstlab.domain.usecase.LogoutUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::LoginUseCase)
    factoryOf(::GetProfileDataUseCase)
    factoryOf(::GetJournalDataUseCase)
    factoryOf(::GetStatisticsDataUseCase)
    factoryOf(::CreateEmotionUseCase)
    factoryOf(::GetEmotionByIdUseCase)
    factoryOf(::CreateNotificationUseCase)
    factoryOf(::RemoveNotificationUseCase)
    factoryOf(::UpdateUserUseCase)
    factoryOf(::ScheduleNotificationsUseCase)
    factoryOf(::CancelNotificationsUseCase)
    factoryOf(::LogoutUseCase)
}