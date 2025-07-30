package planmate.domain.repository

import planmate.domain.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UsersRepository {
    suspend fun getAllUsers(): List<User>

    suspend fun createUser(user: User)

    suspend fun updateUser(user: User)

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteUser(userId: Uuid)
}