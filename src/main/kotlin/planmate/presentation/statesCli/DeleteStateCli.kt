package planmate.presentation.statesCli

import planmate.domain.models.State
import planmate.domain.usecase.statesUseCases.DeleteStateUseCase
import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.projectsCli.ShowProjectsCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteStateCli(private val deleteStateUseCase: DeleteStateUseCase,
                     private val getAllStatesUseCase: GetAllStatesUseCase,
                     private val showProjectsCli: ShowProjectsCli) {

    private lateinit var state: State
    private lateinit var stateName: String
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid

    @OptIn(ExperimentalUuidApi::class)
    fun deleteState(){
        try {
            showProjects()
            enterProjectId()
            enterStateName()
            findState()

            deleteStateUseCase.invoke(state.id)
            ConsoleIO.writeSuccess("deleted successfully!")
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
        ConsoleIO.write("Enter state name: ")
        stateName= ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun findState(){
        state = getAllStatesUseCase.invoke(projectId).first { it.name == stateName }
    }
}