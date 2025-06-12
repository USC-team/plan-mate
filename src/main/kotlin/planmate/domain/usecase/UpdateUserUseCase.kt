package planmate.domain.usecase

import planmate.domain.models.User
import planmate.domain.repository.UsersRepository

class UpdateUserUseCase(private val repo: UsersRepository) {
    fun updateUser(user: User)= repo.updateUser(user)
}