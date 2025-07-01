package planmate.domain.usecase.projectsUseCases

import planmate.domain.models.Project
import planmate.domain.models.User
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.exceptions.InvalidRoleException
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import kotlin.uuid.ExperimentalUuidApi

class CreateProjectUseCase(private val repo: ProjectRepository, private val getAllUsersUseCase: GetAllUsersUseCase) {

    private lateinit var project:Project
    private lateinit var user:User

    fun createProject(project: Project){
        this.project=project
        findUser()

        if (user.role!=ADMIN)
            throw InvalidRoleException()
        else
            repo.createProject(project)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun findUser(){
        user = getAllUsersUseCase.getAllUsers().first { it.id == project.userId }
    }
    companion object{
        private val ADMIN= User.Role.ADMIN
    }
}