package planmate.presentation.projectsCli

import kotlinx.coroutines.runBlocking
import planmate.domain.models.Project
import planmate.domain.models.Task
import planmate.domain.models.State
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.collections.getOrNull
import kotlin.uuid.ExperimentalUuidApi

class ShowProjectsSwimlanes(private val getAllProjectsUseCase: GetAllProjectsUseCase,
                            private val getAllStatesUseCase: GetAllStatesUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase) {

    private var states: List<State> = emptyList()
    private var tasks: List<Task> = emptyList()
    private var tasksOfState :Map<State, List<Task>> =emptyMap()

    @OptIn(ExperimentalUuidApi::class)
    fun showProjects() {
        runBlocking {
            runCatching {
                getAllProjectsUseCase.getAllProjects().takeIf { it.isNotEmpty() }
                    ?: throw Exception("")
            }.onSuccess { projects ->
                projects.forEach { project ->
                    getStatesAndTasks(project)
                    printProjects(project)
                    printStates(tasksOfState)
                    printTasks(tasksOfState)
                    writeLineBreak()
                }
            }.onFailure { e ->
                ConsoleIO.writeError("No projects to show\n${e.message}")
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun getStatesAndTasks(project: Project){
        runBlocking {
            states = getAllStatesUseCase.getAllStates(project.id)
            tasks = getAllTasksUseCase.getAllTasks(project.id)

            tasksOfState = states.associateWith { state ->
                tasks.filter { it.stateId == state.id }
            }
        }
    }

    private fun printProjects(project: Project){
        ConsoleIO.writeSuccess("${project.name} (${tasks.size}):")
    }

    private fun printStates(tasksOfState: Map<State , List<Task>>){
        val headers = states.map {
            "%-${COLUMN_WIDTH}s".format("${it.name} (${tasksOfState[it]?.size ?: 0})")
        }
        ConsoleIO.write(headers.joinToString("|"))
    }

    private fun printTasks(tasksOfState: Map<State , List<Task>>){
        val maxRows = tasksOfState.values.maxOfOrNull { it.size } ?: 0

        for (i in 0 until maxRows) {
            val row = states.map { state ->
                val taskTitle = tasksOfState[state]?.getOrNull(i)?.title ?: ""
                "%-${COLUMN_WIDTH}s".format(taskTitle)
            }
            ConsoleIO.write(row.joinToString("|"))
        }
    }

    private fun writeLineBreak(){
        ConsoleIO.write("_".repeat(COLUMN_WIDTH * states.size))
    }

    companion object{
        private const val COLUMN_WIDTH = 15
    }
}