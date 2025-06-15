package planmate.domain.usecase.tasksUseCases

import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository

class UpdateTaskUseCase(private val repo: TasksRepository) {
    operator fun invoke(task: Task) = repo.updateTask(task)
}