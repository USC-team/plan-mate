package planmate.data.repository

import planmate.data.dto.ProjectDto
import planmate.data.repository.datasource.ProjectsDataSource
import planmate.data.repository.datasource.UsersDataSource
import planmate.domain.models.Project
import planmate.domain.models.User
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.exceptions.InvalidRoleException
import planmate.domain.usecase.exceptions.NameCantBeNullException
import planmate.domain.usecase.exceptions.UserNotFoundException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProjectsRepositoryImp(
    private val projectsDataSource: ProjectsDataSource, private val usersDataSource: UsersDataSource
) : ProjectRepository {

    override fun getAllProjects(): List<Project> {
        return projectsDataSource.getAllProjects()
            .map { it.toDomain() }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createProject(project: Project) {
        val user = getUser(project)
        if (user.role == User.Role.ADMIN){
        if (project.name.trim().isNotEmpty()) {
                projectsDataSource.createProject(ProjectDto.fromDomain(project))
        } else
            throw NameCantBeNullException()
        }else{
            throw InvalidRoleException()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun updateProject(project: Project) {
        projectsDataSource.updateProject(ProjectDto.fromDomain(project))
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProject(projectId: Uuid) {
        projectsDataSource.deleteProject(projectId.toString())
    }

    @OptIn(ExperimentalUuidApi::class)
     fun getUser(project: Project): User {
        return usersDataSource.getAllUsers().map { it.toDomain() }
            .find { it.id == project.userId } ?: throw UserNotFoundException()

    }
}