package planmate.domain.usecase

import planmate.domain.models.Project
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.exceptions.InvalidRoleException
import planmate.domain.usecase.exceptions.NameCantBeNullException

class CreateProjectUsecase(private val repo: ProjectRepository) {

    public fun createProject(project: Project, user:User){
        if(user.role==ADMIN && project.name.isNotEmpty())
            repo.createProject(project)
        else if (user.role!=ADMIN)
            throw InvalidRoleException()
        else
            throw NameCantBeNullException()
        //move exceptions to repo impl
    }
    companion object{
        private val ADMIN= Role.ADMIN
    }
}