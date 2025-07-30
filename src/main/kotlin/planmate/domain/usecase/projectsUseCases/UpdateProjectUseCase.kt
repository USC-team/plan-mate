package planmate.domain.usecase.projectsUseCases

import planmate.domain.models.Project
import planmate.domain.repository.ProjectRepository

class UpdateProjectUseCase(private val repo: ProjectRepository) {
    suspend fun updateProject(project: Project)= repo.updateProject(project)
}