package planmate.data.repository

import planmate.data.dto.ProjectDto
import planmate.data.repository.datasource.ProjectsDataSource
import planmate.domain.models.Project
import planmate.domain.repository.ProjectRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProjectsRepositoryImp(
    private val projectsDataSource: ProjectsDataSource,
) : ProjectRepository {

    override fun getAllProjects(): List<Project> {
        return projectsDataSource.getAllProjects()
            .map { it.toDomain() }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createProject(project: Project) {
        projectsDataSource.createProject(ProjectDto.fromDomain(project))
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun updateProject(project: Project) {
        projectsDataSource.updateProject(ProjectDto.fromDomain(project))
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProject(projectId: Uuid) {
       projectsDataSource.deleteProject(projectId.toString())
    }
}