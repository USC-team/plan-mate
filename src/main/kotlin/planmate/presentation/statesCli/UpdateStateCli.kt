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

    private lateinit var state: State
    private lateinit var stateName: String
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid

    fun updateState(){
        try {
            showProjects()
            enterProjectId()
            enterStateName()
            findState()

            updateStateUseCase.invoke(state)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("State is not valid!\n${e.message}")
        }
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

    private fun enterStateName(){
        stateName= ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun findState(){
        state = getAllStatesUseCase.invoke(projectId).first { it.name == stateName }
    }
}