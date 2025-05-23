package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.UserEntity
import com.example.firstlab.domain.repository.SettingsRepository

class UpdateUserUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(user: UserEntity) {
        repository.updateUser(user)
    }
}