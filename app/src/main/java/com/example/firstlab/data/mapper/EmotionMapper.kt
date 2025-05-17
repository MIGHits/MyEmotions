package com.example.firstlab.data.mapper

import com.example.firstlab.data.model.EmotionDbModel
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.models.EmotionType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EmotionMapper(private val auth: FirebaseAuth) {
    fun mapToDbModel(domainEntity: EmotionEntity): EmotionDbModel {
        return EmotionDbModel(
            id = domainEntity.id,
            name = domainEntity.name,
            type = domainEntity.type ?: EmotionType.GREEN,
            userId = auth.currentUser?.uid ?: "",
            createTime = domainEntity.createTime ?: System.currentTimeMillis(),
            iconRes = domainEntity.iconRes ?: 0,
            actions = domainEntity.actions,
            company = domainEntity.company,
            location = domainEntity.location
        )
    }

    fun mapToDomain(dbEntity: EmotionDbModel): EmotionEntity {
        return EmotionEntity(
            id = dbEntity.id,
            name = dbEntity.name,
            type = dbEntity.type,
            userId = dbEntity.userId,
            createTime = dbEntity.createTime,
            iconRes = dbEntity.iconRes,
            actions = dbEntity.actions,
            company = dbEntity.company,
            location = dbEntity.location
        )
    }

    fun mapFlow(dbFlow: Flow<List<EmotionDbModel>>): Flow<List<EmotionEntity>> {
        return dbFlow.map { it.map { mapToDomain(it) } }
    }
}