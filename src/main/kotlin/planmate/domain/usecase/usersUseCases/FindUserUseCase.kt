package planmate.domain.usecase.usersUseCases

import planmate.domain.models.User
import planmate.domain.repository.UsersRepository

class FindUserUseCase (private val repo: UsersRepository) {
    fun findUser(userName:String): User {
        return repo.getAllUsers().find { it.name==userName }
            ?: throw Exception("user not found ")
    }
}