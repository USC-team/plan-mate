package planmate.presentation.projectsCli

import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class ShowProjectsCli(private val getAllProjectsUseCase: GetAllProjectsUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun showProjects(){
        runCatching {
            getAllProjectsUseCase.getAllProjects().takeIf { it.isNotEmpty() }
                ?: throw Exception("")
        }.onSuccess { projects ->
            projects.forEach { ConsoleIO.writeSuccess("${it.id}\t${it.name}\t ${it.userId}") }
        }.onFailure { e ->
            ConsoleIO.writeError("No projects to show\n${e.message}")
        }
    }
}