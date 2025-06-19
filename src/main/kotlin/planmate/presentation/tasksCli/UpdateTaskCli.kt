package planmate.presentation.tasksCli

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

    private lateinit var task: Task
    private var taskTitle: String=""
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid
    private lateinit var newTask: Task
    private var newTaskTitle: String=""
    private var newTaskDescription: String=""
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var newTaskStateId:Uuid
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var newProjectId: Uuid

    fun updateTask(){
        try {
            findTask()
            buildUpdatedTask()

            updateTaskUseCase.invoke(newTask)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Task is not valid!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun findTask(){
        showProjects()
        enterProjectId()
        enterTaskTitle()
        task = getAllTasksUseCase.invoke(projectId).first { it.title == taskTitle }
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
        ConsoleIO.write("Enter task title: ")
        taskTitle= ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUpdatedTask(){
        enterNewTaskTitle()
        enterNewTaskDescription()
        enterNewStateId()
        enterNewProjectId()
        newTask= Task(task.id, newTaskTitle, newTaskDescription, newTaskStateId, newProjectId)
    }

    private fun enterNewTaskTitle(){
        ConsoleIO.write("Enter new task title: ")
        newTaskTitle= ConsoleIO.read()
    }

    private fun enterNewTaskDescription(){
        ConsoleIO.write("Enter new task description: ")
        newTaskDescription= ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterNewStateId(){
        ConsoleIO.write("Enter new state id: ")
        newTaskStateId = Uuid.parse(ConsoleIO.read())
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterNewProjectId(){
        ConsoleIO.write("Enter new project id: ")
        newProjectId = Uuid.parse(ConsoleIO.read())
    }
}