package com.example.firstlab.di

import androidx.credentials.CredentialManager
import com.example.firstlab.presentation.viewModel.AuthViewModel
import com.example.firstlab.presentation.viewModel.CreateEmotionViewModel
import com.example.firstlab.presentation.viewModel.JournalViewModel
import com.example.firstlab.presentation.viewModel.SettingsViewModel
import com.example.firstlab.presentation.viewModel.StatisticsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth> {
        Firebase.auth
    }
    single {
        CredentialManager.create(get())
    }
    viewModelOf(::AuthViewModel)
    viewModel { CreateEmotionViewModel(get()) }
    viewModelOf(::JournalViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::StatisticsViewModel)
}