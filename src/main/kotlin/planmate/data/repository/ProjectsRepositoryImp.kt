package planmate.data.repository

import planmate.data.dto.ProjectDto
import planmate.data.repository.datasource.ProjectsDataSource
import planmate.domain.models.Project
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.exceptions.NameCantBeNullException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProjectsRepositoryImp(
    private val projectsDataSource: ProjectsDataSource,
) : ProjectRepository {

    override fun getAllProjects(): List<Project> {
        try {
            return projectsDataSource.getAllProjects().drop(1)
                .map { it.toDomain() }
        }
        catch (e: Exception){
            throw Exception("Could not get projects", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createProject(project: Project) {
        try {
            if (project.name.isNotEmpty()) {
                projectsDataSource.createProject(ProjectDto.fromDomain(project))
            } else
                throw NameCantBeNullException()
        }
        catch (e: Exception){
            throw Exception("Could not create project", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun updateProject(project: Project) {
        try {
            projectsDataSource.updateProject(ProjectDto.fromDomain(project))
        }
        catch(e: Exception) {
            throw Exception("Could not update project", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProject(projectId: Uuid) {
        try {
            projectsDataSource.deleteProject(projectId.toString())
        }
        catch(e: Exception) {
            throw Exception("Could not delete project", e)
        }
    }
}