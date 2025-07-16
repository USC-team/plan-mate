package planmate.data.repository

import planmate.data.mapper.fromDomain
import planmate.data.mapper.toDomain
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
        return usersDataSource.getAllUsers()
            .map { it.toDomain() }
    }

    override fun createUser(user: User) {
        if (user.name.isNotEmpty()) {
            usersDataSource.createUser(user.fromDomain(user))
        } else
            throw NameCantBeNullException()
    }

    override fun updateUser(user: User) {
        usersDataSource.updateUser(user.fromDomain(user))
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteUser(userId: Uuid) {
       usersDataSource.deleteUser(userId)
    }
}