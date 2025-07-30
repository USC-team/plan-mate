package planmate.domain.repository


import planmate.domain.models.Task
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface TasksRepository {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getAllTasks(projectId: Uuid): List<Task>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getTasksByState(projectId: Uuid, stateId: Uuid): List<Task>

    suspend fun createTask(task: Task)

    suspend fun updateTask(task: Task)

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteTask(taskId: Uuid)
}
