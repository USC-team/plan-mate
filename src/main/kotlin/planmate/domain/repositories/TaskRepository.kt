package planmate.domain.repositories

import planmate.domain.models.Task

interface TaskRepository {
    fun addTask(task : Task)
}