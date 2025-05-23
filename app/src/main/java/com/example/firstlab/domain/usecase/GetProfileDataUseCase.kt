package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.entity.UserEntity
import com.example.firstlab.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetProfileDataUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Pair<UserEntity, List<NotificationEntity>>> {
        return repository.getSettingsData()
    }
}