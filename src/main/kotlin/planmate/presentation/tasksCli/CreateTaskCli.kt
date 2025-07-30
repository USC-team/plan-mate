package planmate.presentation.tasksCli

import kotlinx.coroutines.runBlocking
import planmate.domain.models.Task
import planmate.domain.usecase.tasksUseCases.CreateTaskUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.statesCli.ShowStatesCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateTaskCli(private val createTaskUseCase:CreateTaskUseCase,
                    private val showStatesCli: ShowStatesCli) {

    fun createTask() {
        try {
            val task= buildTask()
            runBlocking { createTaskUseCase.createTask(task) }
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create task!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildTask() :Task{
        val taskTitle= enterTaskTitle()
        val taskDescription= enterTaskDescription()
        val projectId= showStates()
        val stateId = enterStateId()
        return Task(Uuid.random(), taskTitle, taskDescription, stateId, projectId)
    }

    private fun enterTaskTitle() : String{
        ConsoleIO.write("Enter task title: ")
        return ConsoleIO.read()
    }

    private fun enterTaskDescription(): String {
        ConsoleIO.write("Enter task description: ")
        return ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun showStates(): Uuid{
        ConsoleIO.write("List of States: ")
        showStatesCli.showStates()
        return showStatesCli.enterProjectId()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterStateId() : Uuid{
       ConsoleIO.write("Enter state id: ")
        return Uuid.parse(ConsoleIO.read())
    }
}