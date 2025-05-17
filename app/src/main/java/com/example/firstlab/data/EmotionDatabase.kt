package com.example.firstlab.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.firstlab.App
import com.example.firstlab.data.converter.DateConverter
import com.example.firstlab.data.converter.ListConverter
import com.example.firstlab.data.converter.TimeConverter
import com.example.firstlab.data.dao.EmotionDao
import com.example.firstlab.data.dao.NotificationDao
import com.example.firstlab.data.dao.UserDao
import com.example.firstlab.data.model.EmotionDbModel
import com.example.firstlab.data.model.NotificationDbModel
import com.example.firstlab.data.model.UserDbModel

@Database(
    version = 2,
    exportSchema = false,
    entities = [EmotionDbModel::class, NotificationDbModel::class, UserDbModel::class]
)
@TypeConverters(DateConverter::class, ListConverter::class, TimeConverter::class)
abstract class EmotionDatabase : RoomDatabase() {
    abstract fun EmotionDao(): EmotionDao
    abstract fun NotificationDao(): NotificationDao
    abstract fun UserDao(): UserDao

    companion object {
        fun getDatabase(): EmotionDatabase {
            return Room.databaseBuilder(
                context = App.app,
                EmotionDatabase::class.java,
                "MyEmotionsDatabase"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}