package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.usecase.projectsUseCases.DeleteProjectUseCase
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class DeleteProjectCli(private val deleteProjectUseCase: DeleteProjectUseCase,
                       private val getAllProjectsUseCase: GetAllProjectsUseCase) {
    private lateinit var project: Project
    private lateinit var projectName: String

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProject(){
        try {
            findProject()
            deleteProjectUseCase.deleteProject(project.id)
            ConsoleIO.writeSuccess("deleted successfully!")
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
}