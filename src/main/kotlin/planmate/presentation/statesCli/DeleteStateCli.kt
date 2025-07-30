package planmate.presentation.statesCli

import kotlinx.coroutines.runBlocking
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


    @OptIn(ExperimentalUuidApi::class)
    fun deleteState(){
        runBlocking {
            try {
                val state= findState()
                deleteStateUseCase.deleteState(state.id)
                ConsoleIO.writeSuccess("deleted successfully!")
            }
            catch (e: Exception){
                ConsoleIO.writeError("State is not valid!\n${e.message}")
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun findState():State{
        showProjects()
        val projectId= enterProjectId()
        val stateName= enterStateName()
        return getAllStatesUseCase.getAllStates(projectId).first { it.name == stateName }
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
}