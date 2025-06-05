package planmate.domain.usecase

import planmate.domain.repository.StatesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteStateUseCase(private val repo: StatesRepository) {
    @OptIn(ExperimentalUuidApi::class)
    operator fun invoke(stateId: Uuid) = repo.deleteState(stateId)
}