package planmate.data.repository.datasource

import planmate.data.dto.TaskDto

interface TasksDataSource {

    fun getAllTasks(projectId: String): List<TaskDto>
    fun getTasksByState(projectId: String, stateId: String): List<TaskDto>
    fun createTask(task: TaskDto)
    fun updateTask(task: TaskDto)
    fun deleteTask(taskId: String)
}