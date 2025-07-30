package planmate.domain.usecase.tasksUseCases

import planmate.domain.repository.TasksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetAllTasksUseCase(private val repo: TasksRepository) {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getAllTasks(projectId: Uuid) = repo.getAllTasks(projectId)
}