package planmate.domain.usecase.usersUseCases

import planmate.domain.repository.UsersRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteUserUseCase(private val repo: UsersRepository) {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteUser(userId: Uuid)= repo.deleteUser(userId)
}