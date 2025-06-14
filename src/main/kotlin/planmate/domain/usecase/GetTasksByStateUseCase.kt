package planmate.domain.usecase

import planmate.domain.repository.TasksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetTasksByStateUseCase(private val repo: TasksRepository) {
    @OptIn(ExperimentalUuidApi::class)
    operator fun invoke(projectId: Uuid, stateId: Uuid) = repo.getTasksByState(projectId, stateId)
}