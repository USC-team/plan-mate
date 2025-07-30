package planmate.presentation.tasksCli

import kotlinx.coroutines.runBlocking
import planmate.domain.models.Task
import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import planmate.domain.usecase.tasksUseCases.UpdateTaskUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.projectsCli.ShowProjectsCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UpdateTaskCli(private val updateTaskUseCase: UpdateTaskUseCase,
                    private val getAllTasksUseCase: GetAllTasksUseCase,
                    private val showProjectsCli: ShowProjectsCli) {

    fun updateTask(){
        runBlocking {
            try {
                val task= findTask()
                val newTask= buildUpdatedTask(task)

                updateTaskUseCase.updateTask(newTask)

                ConsoleIO.writeSuccess("updated successfully!")
            }
            catch (e: Exception){
                ConsoleIO.writeError("Task is not valid!\n${e.message}")
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun findTask():Task{
        showProjects()
        val projectId= enterProjectId()
        val taskTitle= enterTaskTitle()
        return getAllTasksUseCase.getAllTasks(projectId).first { it.title == taskTitle }
    }

    private fun showProjects(){
        ConsoleIO.write("List of Projects: ")
        showProjectsCli.showProjects()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterProjectId():Uuid{
        ConsoleIO.write("Enter project id: ")
        return Uuid.parse(ConsoleIO.read())
    }

    private fun enterTaskTitle():String{
        ConsoleIO.write("Enter task title: ")
        return ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUpdatedTask(task: Task):Task{
        val newTaskTitle = enterNewTaskTitle()
        val newTaskDescription= enterNewTaskDescription()
        val newTaskStateId= enterNewStateId()
        val newProjectId= enterNewProjectId()
        return Task(task.id, newTaskTitle, newTaskDescription, newTaskStateId, newProjectId)
    }

    private fun enterNewTaskTitle():String{
        ConsoleIO.write("Enter new task title: ")
        return ConsoleIO.read()
    }

    private fun enterNewTaskDescription():String{
        ConsoleIO.write("Enter new task description: ")
        return ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterNewStateId(): Uuid{
        ConsoleIO.write("Enter new state id: ")
        return Uuid.parse(ConsoleIO.read())
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterNewProjectId(): Uuid{
        ConsoleIO.write("Enter new project id: ")
        return Uuid.parse(ConsoleIO.read())
    }
}