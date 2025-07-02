package planmate.presentation.statesCli

import planmate.domain.models.State
import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import planmate.domain.usecase.statesUseCases.UpdateStateUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.projectsCli.ShowProjectsCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UpdateStateCli(private val updateStateUseCase: UpdateStateUseCase,
                     private val getAllStatesUseCase: GetAllStatesUseCase,
                     private val showProjectsCli: ShowProjectsCli) {

    fun updateState(){
        try {
            val state= findState()
            val newState = buildUpdatedState(state)

            updateStateUseCase.invoke(newState)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("State is not valid!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun findState(): State{
        showProjects()
        val projectId= enterProjectId()
        val stateName= enterStateName()
        return getAllStatesUseCase.invoke(projectId).first { it.name == stateName }
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

    private fun enterStateName(): String{
        ConsoleIO.write("Enter state name: ")
        return ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUpdatedState(state: State): State{
        val newProjectId = enterNewProjectId()
        val newStateName= enterNewStateName()
        return State(state.id, newStateName, newProjectId)
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterNewProjectId(): Uuid{
        ConsoleIO.write("Enter new project id: ")
        return Uuid.parse(ConsoleIO.read())
    }

    private fun enterNewStateName(): String{
        ConsoleIO.write("Enter new state name: ")
        return ConsoleIO.read()
    }
}