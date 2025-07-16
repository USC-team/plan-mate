package planmate.domain.usecase.usersUseCases

import planmate.domain.models.User
import planmate.domain.repository.UsersRepository

class UpdateUserUseCase(private val repo: UsersRepository) {
    fun updateUser(user: User)= repo.updateUser(user)
}