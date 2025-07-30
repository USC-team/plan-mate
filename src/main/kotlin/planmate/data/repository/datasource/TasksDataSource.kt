package planmate.data.repository.datasource

import planmate.data.dto.TaskDto

interface TasksDataSource {

    suspend fun getAllTasks(projectId: String): List<TaskDto>
    suspend fun getTasksByState(projectId: String, stateId: String): List<TaskDto>
    suspend fun createTask(task: TaskDto)
    suspend fun updateTask(task: TaskDto)
    suspend fun deleteTask(taskId: String)
}