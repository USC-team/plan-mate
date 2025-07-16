package planmate.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.dto.ProjectDto
import planmate.data.dto.UserDto
import planmate.data.mapper.toDomain
import planmate.data.repository.datasource.ProjectsDataSource
import planmate.data.repository.datasource.UsersDataSource
import planmate.domain.models.Project
import planmate.domain.models.User.Role
import planmate.domain.usecase.exceptions.InvalidRoleException
import planmate.domain.usecase.exceptions.NameCantBeNullException
import planmate.domain.usecase.exceptions.UserNotFoundException
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class ProjectsRepositoryImpTest {
    private val mockProjectsDataSource = mockk<ProjectsDataSource>(relaxed = true)
    private val mockUserDataSource = mockk<UsersDataSource>(relaxed = true)
    private val repo = ProjectsRepositoryImp(mockProjectsDataSource, mockUserDataSource)

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllProjects should return all projects when data source returns DTOs`() {
        // Given
        val userId = random().toString()
        val dto1 = ProjectDto(
            id = random().toString(),
            name = "Project1",
            userId = userId,
        )
        val dto2 = ProjectDto(
            id = random().toString(),
            name = "Project2",
            userId = userId,
        )
        every { mockProjectsDataSource.getAllProjects() } returns listOf(dto1, dto2)

        // When
        val result = repo.getAllProjects()

        // Then
        assertThat(result).containsExactlyElementsIn(listOf(dto1.toDomain(), dto2.toDomain()))
    }

    @Test
    fun `getAllProjects should return empty list when data source returns empty list`() {
        // Given
        every { mockProjectsDataSource.getAllProjects() } returns emptyList()

        // When
        val result = repo.getAllProjects()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getAllProjects should throw exception when data source fails`() {
        // Given
        every { mockProjectsDataSource.getAllProjects() } throws RuntimeException("I/O error")

        // When && Then
        assertThrows<Exception> { repo.getAllProjects() }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should delegate to data source with correct DTO when user is admin and name is not empty`() {
        // Given
        val projectId = random()
        val userId = random()
        val user = UserDto(id = userId.toString(), name = "Ahmed", role = Role.ADMIN.toString())
        val project = Project(id = projectId, name = "Project1", userId = userId)

        every { mockUserDataSource.getAllUsers() } returns listOf(user)

        // When
        repo.createProject(project)

        // Then
        verify(exactly = 1) {
            mockProjectsDataSource.createProject(
                match { dto ->
                    dto.id == projectId.toString() &&
                            dto.name == "Project1" &&
                            dto.userId == userId.toString()
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should wrap exception when data source fails`() {
        // Given
        val userId = random()
        val user = UserDto(id = userId.toString(), name = "Ahmed", role = Role.ADMIN.toString())
        val project = Project(id = random(), name = "Project1", userId = userId)

        every { mockUserDataSource.getAllUsers() } returns listOf(user)
        every { mockProjectsDataSource.createProject(any()) } throws RuntimeException("Disk full")

        // When & Then
        assertThrows<Exception> { repo.createProject(project) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw NameCantBeNullException when project name is empty and user is admin`() {
        // Given
        val projectId = random()
        val userId = random()
        val user = UserDto(id = userId.toString(), name = "Ahmed", role = Role.ADMIN.toString())
        val project = Project(id = projectId, name = "", userId = userId)
        every { mockUserDataSource.getAllUsers() } returns listOf(user)

        // Then
        assertFailsWith<NameCantBeNullException> {
            repo.createProject(project)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw NameCantBeNullException when project name is blank and user is admin`() {
        // Given
        val projectId = random()
        val userId = random()
        val user = UserDto(id = userId.toString(), name = "Ahmed", role = Role.ADMIN.toString())
        val project = Project(id = projectId, name = "   ", userId = userId) // blank name
        every { mockUserDataSource.getAllUsers() } returns listOf(user)

        // Then
        assertFailsWith<NameCantBeNullException> {
            repo.createProject(project)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw InvalidRoleException when user is MATE even with valid project name`() {
        // Given
        val userId = random()
        val projectId = random()
        val project = Project(
            id = projectId,
            name = "Valid Project Name",
            userId = userId
        )
        val user = UserDto(id = userId.toString(), name = "Ahmed", role = Role.MATE.toString())
        every { mockUserDataSource.getAllUsers() } returns listOf(user)

        // Then
        assertFailsWith<InvalidRoleException> {
            repo.createProject(project)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw InvalidRoleException even if project name is empty and user is mate`() {
        // Given
        val userId = random()
        val projectId = random()
        val project = Project(
            id = projectId,
            name = "", // name is empty
            userId = userId
        )
        val user = UserDto(id = userId.toString(), name = "Ahmed", role = Role.MATE.toString())
        every { mockUserDataSource.getAllUsers() } returns listOf(user)

        // Then
        assertFailsWith<InvalidRoleException> {
            repo.createProject(project)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw UserNotFoundException when user is not found`() {
        // Given
        val userId = random()
        val project = Project(id = random(), name = "Test Project", userId = userId)
        every { mockUserDataSource.getAllUsers() } returns emptyList()

        // Then
        assertFailsWith<UserNotFoundException> {
            repo.createProject(project)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw UserNotFoundException when user list contains different users`() {
        // Given
        val userId = random()
        val differentUserId = random()
        val project = Project(id = random(), name = "Test Project", userId = userId)
        val differentUser = UserDto(id = differentUserId.toString(), name = "Different User", role = Role.ADMIN.toString())
        every { mockUserDataSource.getAllUsers() } returns listOf(differentUser)

        // Then
        assertFailsWith<UserNotFoundException> {
            repo.createProject(project)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateProject should delegate to data source with correct DTO`() {
        // Given
        val projectId = random()
        val userId = random()
        val project = Project(id = projectId, name = "Updated Project", userId = userId)

        // When
        repo.updateProject(project)

        // Then
        verify(exactly = 1) {
            mockProjectsDataSource.updateProject(
                match { dto ->
                    dto.id == projectId.toString() &&
                            dto.name == "Updated Project" &&
                            dto.userId == userId.toString()
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateProject should wrap exception when data source fails`() {
        // Given
        val project = Project(id = random(), name = "Project1", random())
        every { mockProjectsDataSource.updateProject(any()) } throws RuntimeException("Update Error")

        // When & Then
        assertThrows<Exception> { repo.updateProject(project) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteProject should delegate to data source with correct ID`() {
        // Given
        val projectId = random()

        // When
        repo.deleteProject(projectId)

        // Then
        verify(exactly = 1) {
            mockProjectsDataSource.deleteProject(projectId.toString())
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteProject should wrap exception when data source fails`() {
        // Given
        val projectId = random()
        every { mockProjectsDataSource.deleteProject(projectId.toString()) } throws RuntimeException("Delete failure")

        // When & Then
        assertThrows<Exception> { repo.deleteProject(projectId) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getUser should return user when user exists`() {
        // Given
        val userId = random()
        val userDto = UserDto(id = userId.toString(), name = "Ahmed", role = Role.ADMIN.toString())
        val project = Project(id = random(), name = "Test Project", userId = userId)

        every { mockUserDataSource.getAllUsers() } returns listOf(userDto)

        // When
        val result = repo.getUser(project)

        // Then
        assertThat(result.id).isEqualTo(userId)
        assertThat(result.name).isEqualTo("Ahmed")
        assertThat(result.role).isEqualTo(Role.ADMIN)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getUser should return correct user when multiple users exist`() {
        // Given
        val userId1 = random()
        val userId2 = random()
        val userDto1 = UserDto(id = userId1.toString(), name = "Ahmed", role = Role.ADMIN.toString())
        val userDto2 = UserDto(id = userId2.toString(), name = "Sara", role = Role.MATE.toString())
        val project = Project(id = random(), name = "Test Project", userId = userId2)

        every { mockUserDataSource.getAllUsers() } returns listOf(userDto1, userDto2)

        // When
        val result = repo.getUser(project)

        // Then
        assertThat(result.id).isEqualTo(userId2)
        assertThat(result.name).isEqualTo("Sara")
        assertThat(result.role).isEqualTo(Role.MATE)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getUser should throw UserNotFoundException when user does not exist`() {
        // Given
        val userId = random()
        val project = Project(id = random(), name = "Test Project", userId = userId)

        every { mockUserDataSource.getAllUsers() } returns emptyList()

        // Then
        assertFailsWith<UserNotFoundException> {
            repo.getUser(project)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getUser should throw UserNotFoundException when user list contains different users`() {
        // Given
        val userId = random()
        val differentUserId = random()
        val project = Project(id = random(), name = "Test Project", userId = userId)
        val differentUser = UserDto(id = differentUserId.toString(), name = "Different User", role = Role.ADMIN.toString())

        every { mockUserDataSource.getAllUsers() } returns listOf(differentUser)

        // Then
        assertFailsWith<UserNotFoundException> {
            repo.getUser(project)
        }
    }
}