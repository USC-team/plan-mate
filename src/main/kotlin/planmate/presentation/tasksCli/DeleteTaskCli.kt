package planmate.presentation.tasksCli

import planmate.domain.models.Task
import planmate.domain.usecase.tasksUseCases.DeleteTaskUseCase
import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.projectsCli.ShowProjectsCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteTaskCli(private val deleteTaskUseCase: DeleteTaskUseCase,
                    private val getAllTasksUseCase: GetAllTasksUseCase,
                    private val showProjectsCli: ShowProjectsCli) {

    private lateinit var task: Task
    private lateinit var taskTitle: String
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid

    @OptIn(ExperimentalUuidApi::class)
    fun deleteTask(){
        try {
            showProjects()
            enterProjectId()
            enterTaskTitle()
            findTask()

            deleteTaskUseCase.invoke(task.id)
            ConsoleIO.writeSuccess("deleted successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Task is not valid!\n${e.message}")
        }
    }

    private fun showProjects(){
        ConsoleIO.write("List of Projects: ")
        showProjectsCli.showProjects()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterProjectId(){
        ConsoleIO.write("Enter project id: ")
        projectId = Uuid.parse(ConsoleIO.read())
    }

    private fun enterTaskTitle(){
        taskTitle= ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun findTask(){
        task = getAllTasksUseCase.invoke(projectId).first { it.title == taskTitle }
    }
}