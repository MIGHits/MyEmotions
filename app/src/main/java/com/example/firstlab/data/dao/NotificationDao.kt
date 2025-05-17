package com.example.firstlab.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstlab.data.model.NotificationDbModel
import com.example.firstlab.data.model.UserWithNotifications
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNotification(notification: NotificationDbModel)

    @Delete
    suspend fun deleteNotification(notification: NotificationDbModel)
}