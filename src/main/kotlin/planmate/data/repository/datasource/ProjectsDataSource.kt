package planmate.data.repository.datasource

import planmate.data.dto.ProjectDto

interface ProjectsDataSource {

    fun getAllProjects(): List<ProjectDto>

    fun createProject(project: ProjectDto)

    fun updateProject(project: ProjectDto)

    fun deleteProject(projectId: String)
}