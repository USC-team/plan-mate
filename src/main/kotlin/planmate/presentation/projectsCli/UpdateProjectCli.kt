package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.usecase.GetAllProjectsUseCase
import planmate.domain.usecase.UpdateProjectUseCase
import planmate.presentation.ConsoleIO

class UpdateProjectCli(private val updateProjectUseCase: UpdateProjectUseCase,
                       private val getAllProjectsUseCase: GetAllProjectsUseCase) {
    private lateinit var project: Project
    private lateinit var projectName: String

    fun updateProject(){
        try {
            enterProjectName()
            findProject()

            updateProjectUseCase.updateProject(project)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Project is not valid!\n${e.message}")
        }
    }

    private fun enterProjectName(){
        projectName= ConsoleIO.read()
    }

    private fun findProject(){
        project = getAllProjectsUseCase.getAllProjects().first { it.name == projectName }
    }
}