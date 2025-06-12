package planmate.domain.usecase

import planmate.domain.repository.UsersRepository

class GetAllUsersUseCase(private val repo: UsersRepository) {
    fun getAllUsers()= repo.getAllUsers()
}