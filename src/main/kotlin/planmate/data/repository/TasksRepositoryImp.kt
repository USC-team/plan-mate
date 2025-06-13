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
        try {
            return tasksDataSource
                .getAllTasks(projectId.toString())
                .map { it.toDomain() }
        } catch (e: Exception) {
            throw Exception("Could not load tasks for project $projectId", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getTasksByState(projectId: Uuid, stateId: Uuid): List<Task> {
        try {
            return tasksDataSource
                .getTasksByState(projectId.toString(), stateId.toString())
                .map { it.toDomain() }
        } catch (e: Exception) {
            throw Exception("Could not load tasks for project $projectId and state $stateId", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createTask(task: Task) {
        try {
            if (task.title.isBlank()) throw IllegalArgumentException("Task.title must not be blank")
            if (task.description.isBlank()) throw IllegalArgumentException("Task.description must not be blank")

            val existingTask = tasksDataSource.getAllTasks(task.projectId.toString())
                .any { it.id == task.id.toString() }
            if (existingTask) {
                throw IllegalArgumentException("Task with ID ${task.id} already exists in project ${task.projectId}")
            }

            val dto = TaskDto.fromDomain(task)
            tasksDataSource.createTask(dto)
        } catch (e: Exception) {
            throw Exception("Could not create task", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun updateTask(task: Task) {
        try {
            if (task.title.isBlank()) throw IllegalArgumentException("Task.title must not be blank")
            if (task.description.isBlank()) throw IllegalArgumentException("Task.description must not be blank")

            if (tasksDataSource.getAllTasks(task.projectId.toString())
                    .none { it.id == task.id.toString() }
            ) {
                throw IllegalArgumentException("Task ID ${task.id} not found in project ${task.projectId}")
            }

            val dto = TaskDto.fromDomain(task)
            tasksDataSource.updateTask(dto)
        } catch (e: Exception) {
            throw Exception("Could not update task", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteTask(taskId: Uuid) {
        try {
            tasksDataSource.deleteTask(taskId.toString())
        } catch (e: Exception) {
            throw Exception("Could not delete task $taskId", e)
        }
    }
}
