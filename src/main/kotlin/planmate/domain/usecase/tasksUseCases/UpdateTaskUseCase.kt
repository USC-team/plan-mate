package planmate.domain.usecase.tasksUseCases

import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository

class UpdateTaskUseCase(private val repo: TasksRepository) {
    suspend fun updateTask(task: Task) = repo.updateTask(task)
}