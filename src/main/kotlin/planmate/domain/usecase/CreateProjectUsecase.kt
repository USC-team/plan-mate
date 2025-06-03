package planmate.domain.usecase

import planmate.domain.models.Project
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.exceptions.InvalidRoleException

class CreateProjectUsecase(private val repo: ProjectRepository) {

    public fun createProject(project: Project, user:User){
        if(user.role==ADMIN)
            repo.saveProject(project)
        else
            throw InvalidRoleException()

    }
    companion object{
        private val ADMIN= Role.ADMIN
    }
}