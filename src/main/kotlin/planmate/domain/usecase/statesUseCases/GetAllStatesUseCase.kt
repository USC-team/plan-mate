package planmate.domain.usecase.statesUseCases

import planmate.domain.repository.StatesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetAllStatesUseCase(private val repo: StatesRepository) {
    @OptIn(ExperimentalUuidApi::class)
     fun getAllStates(projectId: Uuid) = repo.getAllStates(projectId)
}