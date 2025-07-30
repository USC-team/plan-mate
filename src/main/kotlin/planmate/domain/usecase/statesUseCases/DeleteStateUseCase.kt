package planmate.domain.usecase.statesUseCases

import planmate.domain.repository.StatesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteStateUseCase(private val repo: StatesRepository) {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteState(stateId: Uuid) = repo.deleteState(stateId)
}