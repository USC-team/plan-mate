package planmate.presentation.statesCli

import planmate.domain.usecase.exceptions.NoStatesFoundException
import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ShowStatesCli(private val getAllStatesUseCase: GetAllStatesUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun showStates(){
        runCatching {
            val projectId= enterProjectId()
            getAllStatesUseCase.getAllStates(projectId)
                .takeIf { it.isNotEmpty() }
                ?: throw NoStatesFoundException()
        }.onSuccess { states ->
            states.forEach {
                ConsoleIO.writeSuccess("${it.id}\t${it.name}\t${it.projectId}")
            }
        }.onFailure { e ->
            ConsoleIO.writeError("No states to show\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun enterProjectId(): Uuid{
        ConsoleIO.write("Enter project id:")
        return Uuid.parse(ConsoleIO.read())
    }
}