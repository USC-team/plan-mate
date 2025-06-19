package planmate.presentation.statesCli

import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ShowStatesCli(private val getAllStatesUseCase: GetAllStatesUseCase) {

    @OptIn(ExperimentalUuidApi::class)
    lateinit var projectId: Uuid

    @OptIn(ExperimentalUuidApi::class)
    fun showStates(){
        runCatching {
            enterProjectId()
            getAllStatesUseCase(projectId)
                .takeIf { it.isNotEmpty() }
                ?: throw Exception("")
        }.onSuccess { states ->
            states.forEach {
                ConsoleIO.writeSuccess("${it.id}\t${it.name}\t${it.projectId}")
            }
        }.onFailure { e ->
            ConsoleIO.writeError("No states to show\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterProjectId(){
        ConsoleIO.write("Enter project id:")
        projectId= Uuid.parse(ConsoleIO.read())
    }
}