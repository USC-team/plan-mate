package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.domain.usecase.projectsUseCases.UpdateProjectUseCase
import planmate.presentation.console.ConsoleIO
import planmate.presentation.loginCli.LoginCLI
import kotlin.uuid.ExperimentalUuidApi

class UpdateProjectCli(private val updateProjectUseCase: UpdateProjectUseCase,
                       private val getAllProjectsUseCase: GetAllProjectsUseCase) {

    fun updateProject(){
        try {
            val project= findProject()
            val newProject= buildUpdatedProject(project)

            updateProjectUseCase.updateProject(newProject)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Project is not valid!\n${e.message}")
        }
    }

    private fun findProject() : Project{
        val projectName= enterProjectName()
       return getAllProjectsUseCase.getAllProjects().first { it.name == projectName }
    }

    private fun enterProjectName(): String{
        ConsoleIO.write("Enter project name:")
        return ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUpdatedProject(project: Project) : Project{
        val newProjectName = enterNewProjectName()
        return Project(project.id, newProjectName, LoginCLI.USER.id)
    }

    private fun enterNewProjectName(): String{
        ConsoleIO.write("Enter new project name:")
        return ConsoleIO.read()
    }

}