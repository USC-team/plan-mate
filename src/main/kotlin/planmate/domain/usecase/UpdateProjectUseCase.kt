package planmate.domain.usecase

import planmate.domain.models.Project
import planmate.domain.repository.ProjectRepository

class UpdateProjectUseCase(private val repo: ProjectRepository) {
    fun updateProject(project: Project)= repo.updateProject(project)
}