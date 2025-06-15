package planmate.domain.usecase.usersUseCases

import planmate.domain.repository.UsersRepository

class GetAllUsersUseCase(private val repo: UsersRepository) {
    fun getAllUsers()= repo.getAllUsers()
}