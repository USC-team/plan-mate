package planmate.domain.usecase.tasksUseCases

import planmate.domain.repository.TasksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteTaskUseCase(private val repo: TasksRepository) {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteTask(taskId: Uuid) = repo.deleteTask(taskId)
}