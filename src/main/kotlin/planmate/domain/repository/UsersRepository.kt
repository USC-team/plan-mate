package planmate.domain.repository

import planmate.domain.models.User

interface UsersRepository {
    fun getAllUsers(): List<User>

    fun createUser(user: User)

    fun updateUser(user: User)

    fun deleteUser(userId: String)
}