package planmate.data.repository

import planmate.data.dto.UserDto
import planmate.data.repository.datasource.UsersDataSource
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.exceptions.NameCantBeNullException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UsersRepositoryImp (
    private val usersDataSource: UsersDataSource,
) : UsersRepository {

    override fun getAllUsers(): List<User> {
        try {
            return usersDataSource.getAllUsers().drop(1)
                .map { it.toDomain() }
        }
        catch (e: Exception){
            throw Exception("Could not get users", e)
        }
    }

    override fun createUser(user: User) {
        try {
            if (user.name.isNotEmpty()) {
                usersDataSource.createUser(UserDto.fromDomain(user))
            } else
                throw NameCantBeNullException()
        }
        catch (e: Exception){
            throw Exception("Could not create user", e)
        }
    }

    override fun updateUser(user: User) {
        try {
            usersDataSource.updateUser(UserDto.fromDomain(user))
        }
        catch(e: Exception) {
            throw Exception("Could not update user", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteUser(userId: Uuid) {
        try {
           usersDataSource.deleteUser(userId)
        }
        catch(e: Exception) {
            throw Exception("Could not delete user", e)
        }
    }
}