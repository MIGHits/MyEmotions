package com.example.firstlab.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.firstlab.data.model.UserDbModel
import com.example.firstlab.data.model.UserWithNotifications
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserDbModel)

    @Delete
    suspend fun deleteUser(user: UserDbModel)

    @Update
    suspend fun updateUser(user: UserDbModel)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): UserDbModel?

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithNotifications(userId: String): Flow<List<UserWithNotifications>>?
}