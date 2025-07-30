package planmate.data.repository.datasource

import planmate.data.dto.ProjectDto

interface ProjectsDataSource {

    suspend fun getAllProjects(): List<ProjectDto>

    suspend fun createProject(project: ProjectDto)

    suspend fun updateProject(project: ProjectDto)

    suspend fun deleteProject(projectId: String)
}