package com.example.firstlab

import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.entity.UserEntity
import com.example.firstlab.domain.repository.SettingsRepository
import com.example.firstlab.domain.usecase.CreateNotificationUseCase
import com.example.firstlab.domain.usecase.GetProfileDataUseCase
import com.example.firstlab.domain.usecase.RemoveNotificationUseCase
import com.example.firstlab.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalTime

class SettingsTest {

    private lateinit var repository: FakeSettingsRepository

    @Before
    fun setUp() {
        repository = FakeSettingsRepository()
    }

    @Test
    fun `CreateNotificationUseCase adds notification`() = runTest {
        val useCase = CreateNotificationUseCase(repository)
        val notification = NotificationEntity(id = 1, time = LocalTime.of(8, 0))

        useCase(notification)

        assertTrue(repository.createdNotifications.contains(notification))
        assertTrue(repository.notifications.contains(notification))
    }

    @Test
    fun `RemoveNotificationUseCase deletes notification`() = runTest {
        val notification = NotificationEntity(id = 1, time = LocalTime.of(9, 0))
        repository.notifications.add(notification)

        val useCase = RemoveNotificationUseCase(repository)
        useCase(notification)

        assertTrue(repository.deletedNotifications.contains(notification))
        assertFalse(repository.notifications.contains(notification))
    }

    @Test
    fun `GetProfileDataUseCase returns correct user and notifications`() = runTest {
        val useCase = GetProfileDataUseCase(repository)

        val result = useCase().first()

        assertEquals(repository.userEntity, result.first)
        assertEquals(repository.notifications, result.second)
    }

    @Test
    fun `UpdateUserUseCase updates user biometric setting`() = runTest {
        val newUser = repository.userEntity.copy(isFingerprintEnabled = true)
        val useCase = UpdateUserUseCase(repository)

        useCase(newUser)

        assertEquals(newUser, repository.updatedUser)
        assertTrue(repository.userEntity.isFingerprintEnabled)
    }

    class FakeSettingsRepository : SettingsRepository {

        var userEntity = UserEntity(
            id = "123",
            username = "Test User",
            isNotificationEnabled = false,
            isFingerprintEnabled = false,
            avatar = ""
        )
        var notifications = mutableListOf<NotificationEntity>()

        var createdNotifications = mutableListOf<NotificationEntity>()
        var deletedNotifications = mutableListOf<NotificationEntity>()
        var updatedUser: UserEntity? = null

        override fun getSettingsData(): Flow<Pair<UserEntity, List<NotificationEntity>>> {
            return flowOf(userEntity to notifications)
        }

        override suspend fun createNotification(notification: NotificationEntity) {
            createdNotifications.add(notification)
            notifications.add(notification)
        }

        override suspend fun deleteNotification(notification: NotificationEntity) {
            deletedNotifications.add(notification)
            notifications.removeIf { it.id == notification.id }
        }

        override suspend fun updateUser(userEntity: UserEntity) {
            updatedUser = userEntity
            this.userEntity = userEntity
        }
    }
}