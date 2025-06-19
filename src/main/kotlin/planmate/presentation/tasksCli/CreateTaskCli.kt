package planmate.presentation.tasksCli

import planmate.domain.models.Task
import planmate.domain.usecase.tasksUseCases.CreateTaskUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.statesCli.ShowStatesCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateTaskCli(private val createTaskUseCase:CreateTaskUseCase,
                    private val showStatesCli: ShowStatesCli) {

    private var taskTitle: String=""
    private var taskDescription: String=""
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var stateId: Uuid
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid
    private lateinit var task:Task

    fun createTask() {
        try {
            buildTask()
            createTaskUseCase.invoke(task)
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create task!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildTask() {
        enterTaskTitle()
        enterTaskDescription()
        showStates()
        enterStateId()
        task = Task(Uuid.random(), taskTitle, taskDescription, stateId, projectId)
    }

    private fun enterTaskTitle() {
        ConsoleIO.write("Enter task title: ")
        taskTitle = ConsoleIO.read()
    }

    private fun enterTaskDescription() {
        ConsoleIO.write("Enter task description: ")
        taskDescription = ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun showStates(){
        ConsoleIO.write("List of States: ")
        showStatesCli.showStates()
        projectId=showStatesCli.projectId
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterStateId(){
       ConsoleIO.write("Enter state id: ")
        stateId = Uuid.parse(ConsoleIO.read())
    }
}