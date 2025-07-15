package planmate.domain.usecase.projectsUseCases

import planmate.domain.models.Project
import planmate.domain.models.User
import planmate.domain.models.User.Role
import planmate.domain.repository.ProjectRepository
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.exceptions.InvalidRoleException
import kotlin.uuid.ExperimentalUuidApi

class CreateProjectUseCase(private val repo: ProjectRepository) {

    fun createProject(project: Project) = repo.createProject(project)

}