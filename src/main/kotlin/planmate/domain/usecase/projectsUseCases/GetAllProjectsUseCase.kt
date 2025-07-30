package planmate.domain.usecase.projectsUseCases

import planmate.domain.repository.ProjectRepository

class GetAllProjectsUseCase(private val repo: ProjectRepository) {
    suspend fun getAllProjects()= repo.getAllProjects()
}