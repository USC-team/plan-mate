package planmate.domain.usecase.tasksUseCases

import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository

class CreateTaskUseCase(private val repo: TasksRepository) {
     fun createTask(task: Task) = repo.createTask(task)
}