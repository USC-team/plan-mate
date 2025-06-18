package planmate.presentation.tasksCli

import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ShowTasksCli(private val getAllTasksUseCase: GetAllTasksUseCase) {

    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid

    @OptIn(ExperimentalUuidApi::class)
    fun showTasks(){
        try {
            enterProjectId()
            getAllTasksUseCase.invoke(projectId)
                .forEach { ConsoleIO.writeSuccess("${it.id}\t ${it.title}\t ${it.description}\t " +
                        "${it.stateId}\t ${it.projectId}") }
        }
        catch (e: Exception){
            ConsoleIO.writeError("No tasks to show\n ${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterProjectId(){
        projectId= Uuid.parse(ConsoleIO.read())
    }
}