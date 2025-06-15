package planmate.domain.repository


import planmate.domain.models.Task
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface TasksRepository {
    @OptIn(ExperimentalUuidApi::class)
    fun getAllTasks(projectId: Uuid): List<Task>

    @OptIn(ExperimentalUuidApi::class)
    fun getTasksByState(projectId: Uuid, stateId: Uuid): List<Task>

    fun createTask(task: Task)

    fun updateTask(task: Task)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteTask(taskId: Uuid)
}
