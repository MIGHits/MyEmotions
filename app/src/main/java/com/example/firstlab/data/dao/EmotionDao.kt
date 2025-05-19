package com.example.firstlab.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstlab.data.model.EmotionDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEmotion(emotion: EmotionDbModel)

    @Query("SELECT * FROM Emotions WHERE id = :id")
    suspend fun getEmotionById(id: Int): EmotionDbModel?

    @Query("SELECT * FROM Emotions WHERE userId = :userId AND createTime BETWEEN :start AND :end  ORDER BY createTime DESC")
    fun getEmotionsByPeriod(userId: String, start: Long, end: Long): Flow<List<EmotionDbModel>>

    @Query("SELECT * FROM Emotions WHERE userId = :userId ORDER BY createTime DESC")
    fun getAllEmotions(userId: String): Flow<List<EmotionDbModel>>
}