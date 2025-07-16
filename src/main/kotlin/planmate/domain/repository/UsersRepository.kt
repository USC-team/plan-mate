package planmate.domain.repository

import planmate.domain.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UsersRepository {
    fun getAllUsers(): List<User>

    fun createUser(user: User)

    fun updateUser(user: User)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteUser(userId: Uuid)
}