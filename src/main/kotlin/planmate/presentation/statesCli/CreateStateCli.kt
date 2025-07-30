package planmate.presentation.statesCli

import kotlinx.coroutines.runBlocking
import planmate.domain.models.State
import planmate.domain.usecase.statesUseCases.CreateStateUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.projectsCli.ShowProjectsCli
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateStateCli(private val createStateUseCase:CreateStateUseCase,
                     private val showProjectsCli: ShowProjectsCli) {

    fun createState() {
        try {
            val state= buildState()
            runBlocking {
                createStateUseCase.createState(state)
            }
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create state!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildState(): State {
        val stateName= enterStateName()
        showProjects()
        val projectId= enterProjectId()
        return State(Uuid.random(), stateName, projectId)
    }

    private fun enterStateName() : String{
        ConsoleIO.write("Enter state name: ")
        return ConsoleIO.read()
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
}