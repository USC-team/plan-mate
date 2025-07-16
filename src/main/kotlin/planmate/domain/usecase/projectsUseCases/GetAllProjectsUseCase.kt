package planmate.domain.usecase.projectsUseCases

import planmate.domain.repository.ProjectRepository

class GetAllProjectsUseCase(private val repo: ProjectRepository) {
    fun getAllProjects()= repo.getAllProjects()
}