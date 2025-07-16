package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.usecase.projectsUseCases.CreateProjectUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.loginCli.LoginCLI
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateProjectsCli(private val createProjectUseCase:CreateProjectUseCase) {


    fun createProject() {
        try {
            val project= buildProject()
            createProjectUseCase.createProject(project)
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create project!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildProject() :Project{
        val projectName= enterProjectName()
        return Project(Uuid.random(), projectName, LoginCLI.USER.id)
    }

    private fun enterProjectName(): String {
        ConsoleIO.write("Enter project name: ")
        return ConsoleIO.read()
    }
}