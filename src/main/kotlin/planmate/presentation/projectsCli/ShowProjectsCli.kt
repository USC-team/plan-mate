package planmate.presentation.projectsCli

import planmate.domain.usecase.GetAllProjectsUseCase
import planmate.presentation.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class ShowProjectsCli(private val getAllProjectsUseCase: GetAllProjectsUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun showProjects(){
        try {
            getAllProjectsUseCase.getAllProjects().forEach { ConsoleIO.writeSuccess("${it.id}\t ${it.name}\t") }
        }
        catch (e: Exception){
            ConsoleIO.writeError("No projects to show\n ${e.message}")
        }
    }
}