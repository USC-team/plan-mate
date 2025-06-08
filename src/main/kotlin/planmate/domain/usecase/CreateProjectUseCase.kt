package planmate.domain.usecase

import planmate.domain.models.Project
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.exceptions.InvalidRoleException

class CreateProjectUseCase(private val repo: ProjectRepository) {

    fun createProject(project: Project, user:User){
        if (user.role!=ADMIN)
            throw InvalidRoleException()
        else
            repo.createProject(project)
    }
    companion object{
        private val ADMIN= Role.ADMIN
    }
}