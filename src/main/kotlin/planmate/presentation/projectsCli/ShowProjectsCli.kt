package planmate.presentation.projectsCli

import kotlinx.coroutines.runBlocking
import planmate.domain.usecase.exceptions.NoProjectFoundException
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.loginCli.LoginCLI
import kotlin.uuid.ExperimentalUuidApi

class ShowProjectsCli(private val getAllProjectsUseCase: GetAllProjectsUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun showProjects() {
        runBlocking {
            runCatching {
                getAllProjectsUseCase.getAllProjects().takeIf { it.isNotEmpty() }
                    ?: throw NoProjectFoundException()
            }.onSuccess { projects ->
                projects.filter { it.userId == LoginCLI.USER.id }
                    .forEach { ConsoleIO.writeSuccess("${it.id}\t${it.name}\t ${it.userId}") }
            }.onFailure { e ->
                ConsoleIO.writeError("No projects to show\n${e.message}")
            }
        }
    }
}