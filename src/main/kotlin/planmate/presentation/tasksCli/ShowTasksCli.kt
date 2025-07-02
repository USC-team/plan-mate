package planmate.presentation.tasksCli

import planmate.domain.usecase.exceptions.NoTasksFoundException
import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ShowTasksCli(private val getAllTasksUseCase: GetAllTasksUseCase) {

    @OptIn(ExperimentalUuidApi::class)
    fun showTasks(){
        runCatching {
            val projectId = enterProjectId()
            getAllTasksUseCase(projectId).takeIf { it.isNotEmpty() }
                ?: throw NoTasksFoundException()
        }.onSuccess { tasks ->
            tasks.forEach {
                ConsoleIO.writeSuccess("${it.id}\t${it.title}\t${it.description}\t${it.stateId}\t${it.projectId}")
            }
        }.onFailure { e ->
            ConsoleIO.writeError("No tasks to show\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterProjectId(): Uuid{
        ConsoleIO.write("Enter project id:")
        return Uuid.parse(ConsoleIO.read())
    }
}