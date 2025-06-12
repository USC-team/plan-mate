package planmate.domain.usecase

import planmate.domain.repository.UsersRepository

class DeleteUserUseCase(private val repo: UsersRepository) {
    fun deleteUser(userId: String)= repo.deleteUser(userId)
}