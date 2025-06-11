package planmate.domain.usecase

import planmate.domain.models.User
import planmate.domain.repository.UsersRepository

class FindUserUseCase (private val repo: UsersRepository) {
    fun finUser(userName:String): User {
        return repo.getAllUsers().find { it.name==userName }
            ?: throw Exception("user not found ")
    }
}