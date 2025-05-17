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

    @Query("SELECT * FROM Emotions WHERE createTime BETWEEN :start AND :end  ORDER BY createTime DESC")
    fun getEmotionsByPeriod(start: Long, end: Long): Flow<List<EmotionDbModel>>

}