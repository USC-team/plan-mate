package planmate.presentation.statesCli

import planmate.domain.models.State
import planmate.domain.usecase.statesUseCases.CreateStateUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.projectsCli.ShowProjectsCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateStateCli(private val createStateUseCase:CreateStateUseCase,
                     private val showProjectsCli: ShowProjectsCli) {
    private lateinit var stateName: String
    private lateinit var state: State
    @OptIn(ExperimentalUuidApi::class)
    private lateinit var projectId: Uuid

    fun createState() {
        try {
            buildState()
            createStateUseCase.invoke(state)
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create state!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildState() {
        enterStateName()
        showProjects()
        enterProjectId()
        state = State(Uuid.random(), stateName, projectId)
    }

    private fun enterStateName() {
        ConsoleIO.write("Enter state name: ")
        stateName = ConsoleIO.read()
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

}