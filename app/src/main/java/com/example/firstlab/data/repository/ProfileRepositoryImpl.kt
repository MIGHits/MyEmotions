package com.example.firstlab.data.repository

import com.example.firstlab.data.dao.NotificationDao
import com.example.firstlab.data.dao.UserDao
import com.example.firstlab.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val userDao: UserDao,
    private val notificationDao: NotificationDao
) : ProfileRepository {

}