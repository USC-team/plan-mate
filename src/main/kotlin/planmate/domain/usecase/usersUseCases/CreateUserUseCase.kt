package planmate.domain.usecase.usersUseCases

import planmate.domain.models.User
import planmate.domain.repository.UsersRepository

class CreateUserUseCase(private val repo: UsersRepository) {

    fun createUser(user: User)=repo.createUser(user)
}