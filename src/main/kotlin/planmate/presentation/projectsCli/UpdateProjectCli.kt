package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.domain.usecase.projectsUseCases.UpdateProjectUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.loginCli.LoginCLI
import kotlin.uuid.ExperimentalUuidApi

class UpdateProjectCli(private val updateProjectUseCase: UpdateProjectUseCase,
                       private val getAllProjectsUseCase: GetAllProjectsUseCase) {
    private lateinit var project: Project
    private lateinit var newProject: Project
    private var projectName: String=""
    private var newProjectName: String=""

    fun updateProject(){
        try {
            findProject()
            buildUpdatedProject()

            updateProjectUseCase.updateProject(newProject)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Project is not valid!\n${e.message}")
        }
    }

    private fun findProject(){
        enterProjectName()
        project = getAllProjectsUseCase.getAllProjects().first { it.name == projectName }
    }

    private fun enterProjectName(){
        ConsoleIO.write("Enter project name:")
        projectName= ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUpdatedProject(){
        enterNewProjectName()
        newProject= Project(project.id, newProjectName, LoginCLI.USER.id)
    }

    private fun enterNewProjectName(){
        ConsoleIO.write("Enter new project name:")
        newProjectName= ConsoleIO.read()
    }

}