package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.usecase.DeleteProjectUseCase
import planmate.domain.usecase.GetAllProjectsUseCase
import planmate.domain.usecase.UpdateProjectUseCase
import planmate.presentation.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class DeleteProjectCli(private val deleteProjectUseCase: DeleteProjectUseCase,
                       private val getAllProjectsUseCase: GetAllProjectsUseCase) {
    private lateinit var project: Project
    private lateinit var projectName: String

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProject(){
        try {
            enterProjectName()
            findProject()

            deleteProjectUseCase.deleteProject(project.id)
            ConsoleIO.writeSuccess("deleted successfully!")
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