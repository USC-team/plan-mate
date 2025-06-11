package planmate.data.repository.datasource

import planmate.data.dto.UserDto

interface UsersDataSource {
    fun getAllUsers(): List<UserDto>

    fun createUser(user: UserDto)

    fun updateUser(user: UserDto)

    fun deleteUser(userId: String)
}