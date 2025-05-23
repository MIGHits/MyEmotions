package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.repository.FirebaseAuthRepository

class LogoutUseCase(private val repository: FirebaseAuthRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}