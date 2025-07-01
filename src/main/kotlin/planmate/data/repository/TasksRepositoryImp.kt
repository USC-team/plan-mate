package planmate.data.repository


import planmate.data.dto.TaskDto
import planmate.data.repository.datasource.TasksDataSource
import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TasksRepositoryImp(
    private val tasksDataSource: TasksDataSource,
) : TasksRepository {

    @OptIn(ExperimentalUuidApi::class)
    override fun getAllTasks(projectId: Uuid): List<Task> {
        return tasksDataSource
            .getAllTasks(projectId.toString())
            .map { it.toDomain() }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getTasksByState(projectId: Uuid, stateId: Uuid): List<Task> {
        return tasksDataSource
            .getTasksByState(projectId.toString(), stateId.toString())
            .map { it.toDomain() }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createTask(task: Task) {
        if (task.title.isBlank()) throw IllegalArgumentException("Task.title must not be blank")
        if (task.description.isBlank()) throw IllegalArgumentException("Task.description must not be blank")

        val existingTask = tasksDataSource.getAllTasks(task.projectId.toString())
            .any { it.id == task.id.toString() }
        if (existingTask) {
            throw IllegalArgumentException("Task with ID ${task.id} already exists in project ${task.projectId}")
        }

        val dto = TaskDto.fromDomain(task)
        tasksDataSource.createTask(dto)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun updateTask(task: Task) {
        if (task.title.isBlank()) throw Exception("Task.title must not be blank")
        if (task.description.isBlank()) throw Exception("Task.description must not be blank")

        if (tasksDataSource.getAllTasks(task.projectId.toString())
                .none { it.id == task.id.toString() }
        ) {
            throw Exception("Task ID ${task.id} not found in project ${task.projectId}")
        }

        val dto = TaskDto.fromDomain(task)
        tasksDataSource.updateTask(dto)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteTask(taskId: Uuid) {
        tasksDataSource.deleteTask(taskId.toString())
    }
}