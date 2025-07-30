package planmate.data.repository.datasource

import planmate.data.dto.UserDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UsersDataSource {
    suspend fun getAllUsers(): List<UserDto>

    suspend fun createUser(user: UserDto)

    suspend fun updateUser(user: UserDto)

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteUser(userId: Uuid)
}