package planmate.domain.usecase.tasksUseCases

import planmate.domain.repository.TasksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetTasksByStateUseCase(private val repo: TasksRepository) {
    @OptIn(ExperimentalUuidApi::class)
    fun getTaskByState(projectId: Uuid, stateId: Uuid) = repo.getTasksByState(projectId, stateId)
}