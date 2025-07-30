package planmate.presentation.tasksCli

import kotlinx.coroutines.runBlocking
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

    @OptIn(ExperimentalUuidApi::class)
    fun deleteTask(){
        runBlocking {
            try {
                val task= findTask()

                deleteTaskUseCase.deleteTask(task.id)
                ConsoleIO.writeSuccess("deleted successfully!")
            }
            catch (e: Exception){
                ConsoleIO.writeError("Task is not valid!\n${e.message}")
            }
        }
    }

    private fun showProjects(){
        ConsoleIO.write("List of Projects: ")
        showProjectsCli.showProjects()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterProjectId(): Uuid{
        ConsoleIO.write("Enter project id: ")
        return Uuid.parse(ConsoleIO.read())
    }

    private fun enterTaskTitle():String{
        ConsoleIO.write("Enter task title: ")
        return ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun findTask():Task {
        showProjects()
        val projectId= enterProjectId()
        val taskTitle= enterTaskTitle()
        return getAllTasksUseCase.getAllTasks(projectId).first { it.title == taskTitle }
    }
}