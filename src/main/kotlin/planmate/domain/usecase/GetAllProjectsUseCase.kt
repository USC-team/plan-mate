package planmate.domain.usecase

import planmate.domain.repository.ProjectRepository

class GetAllProjectsUseCase(private val repo: ProjectRepository) {
    fun getAllProjects()= repo.getAllProjects()
}