package planmate.domain.usecase.usersUseCases

import planmate.domain.repository.UsersRepository

class GetAllUsersUseCase(private val repo: UsersRepository) {
    suspend fun getAllUsers()=
        repo.getAllUsers()
}