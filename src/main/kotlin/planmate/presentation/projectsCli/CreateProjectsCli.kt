package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.models.User
import planmate.domain.usecase.projectsUseCases.CreateProjectUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateProjectsCli(private val createProjectUseCase:CreateProjectUseCase, private val creator: User) {
    private lateinit var projectName: String
    private lateinit var project: Project

    fun createProject() {
        try {
            enterProjectName()
            buildProject()
            createProjectUseCase.createProject(project, creator)
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create project!\n${e.message}")
        }
    }

    private fun enterProjectName() {
        ConsoleIO.write("Enter project name: ")
        projectName = ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildProject() {
        project = Project(Uuid.random(), projectName)
    }
}