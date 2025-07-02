package planmate.presentation.projectsCli

import planmate.domain.models.Project
import planmate.domain.usecase.projectsUseCases.DeleteProjectUseCase
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class DeleteProjectCli(private val deleteProjectUseCase: DeleteProjectUseCase,
                       private val getAllProjectsUseCase: GetAllProjectsUseCase) {

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProject(){
        try {
            val project= findProject()
            deleteProjectUseCase.deleteProject(project.id)
            ConsoleIO.writeSuccess("deleted successfully!")
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
}