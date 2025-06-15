package planmate.domain.usecase.tasksUseCases

import planmate.domain.repository.TasksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetAllTasksUseCase(private val repo: TasksRepository) {
    @OptIn(ExperimentalUuidApi::class)
    operator fun invoke(projectId: Uuid) = repo.getAllTasks(projectId)
}