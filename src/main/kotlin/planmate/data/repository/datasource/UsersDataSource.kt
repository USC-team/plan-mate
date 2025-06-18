package planmate.data.repository.datasource

import planmate.data.dto.UserDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UsersDataSource {
    fun getAllUsers(): List<UserDto>

    fun createUser(user: UserDto)

    fun updateUser(user: UserDto)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteUser(userId: Uuid)
}