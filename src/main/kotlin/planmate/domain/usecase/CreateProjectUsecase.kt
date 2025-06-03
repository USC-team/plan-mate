package planmate.domain.usecase

import planmate.domain.models.Project
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.exceptions.InvalidRoleException

class CreateProjectUsecase(private val repo: ProjectRepository) {

    public fun createProject(project: Project, user:User){
        if(user.role==ADMIN && project.name.isNotEmpty())
            repo.saveProject(project)
        else if (user.role!=ADMIN)
            throw InvalidRoleException()
        else
            throw NameCantBeNullException()
    }
    companion object{
        private val ADMIN= Role.ADMIN
    }
}