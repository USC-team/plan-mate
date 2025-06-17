package planmate.presentation.tasksCli

import planmate.domain.models.Task
import planmate.domain.usecase.tasksUseCases.CreateTaskUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.loginCli.LoginCLI
import planmate.presentation.projectsCli.ShowProjectsCli
import planmate.presentation.statesCli.ShowStatesCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateTaskCli(private val createTaskUseCase:CreateTaskUseCase,
                    private val showProjectsCli: ShowProjectsCli,
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
            enterTaskTitle()
            enterTaskDescription()
            showStates()
            enterStateId()
            showProjects()
            enterProjectId()
            buildTask()

            createTaskUseCase.invoke(task)
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create task!\n${e.message}")
        }
    }

    private fun enterTaskTitle() {
        ConsoleIO.write("Enter task title: ")
        taskTitle = ConsoleIO.read()
    }

    private fun enterTaskDescription() {
        ConsoleIO.write("Enter task description: ")
        taskDescription = ConsoleIO.read()
    }

    private fun showStates(){
        ConsoleIO.write("List of States: ")
        showStatesCli.showStates()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterStateId(){
       ConsoleIO.write("Enter state id: ")
        stateId = Uuid.parse(ConsoleIO.read())
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

    @OptIn(ExperimentalUuidApi::class)
    private fun buildTask() {
        task = Task(Uuid.random(), taskTitle, taskDescription, LoginCLI.USER.id , stateId, projectId)
    }
}