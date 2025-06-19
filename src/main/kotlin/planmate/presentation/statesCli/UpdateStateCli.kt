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
    private var stateName: String=""
    private lateinit var newState: State
    private var newStateName: String=""
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var newProjectId: Uuid

    fun updateState(){
        try {
            findState()
            buildUpdatedState()

            updateStateUseCase.invoke(newState)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("State is not valid!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun findState(){
        showProjects()
        enterProjectId()
        enterStateName()
        state = getAllStatesUseCase.invoke(projectId).first { it.name == stateName }
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
    private fun buildUpdatedState(){
        enterNewProjectId()
        enterNewStateName()
        newState= State(state.id, newStateName, newProjectId)
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun enterNewProjectId(){
        ConsoleIO.write("Enter new project id: ")
        newProjectId = Uuid.parse(ConsoleIO.read())
    }

    private fun enterNewStateName(){
        ConsoleIO.write("Enter new state name: ")
        newStateName= ConsoleIO.read()
    }
}