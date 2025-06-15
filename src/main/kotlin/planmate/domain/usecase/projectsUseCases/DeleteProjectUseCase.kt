package planmate.domain.usecase.projectsUseCases

import planmate.domain.repository.ProjectRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteProjectUseCase(private val repo: ProjectRepository) {
    @OptIn(ExperimentalUuidApi::class)
    fun deleteProject(projectId: Uuid)= repo.deleteProject(projectId)
}