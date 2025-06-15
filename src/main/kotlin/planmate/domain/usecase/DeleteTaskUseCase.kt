package planmate.domain.usecase

import planmate.domain.repository.TasksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteTaskUseCase(private val repo: TasksRepository) {
    @OptIn(ExperimentalUuidApi::class)
    operator fun invoke(taskId: Uuid) = repo.deleteTask(taskId)
}