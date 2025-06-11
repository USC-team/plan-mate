package planmate.domain.usecase

import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import planmate.domain.repository.ProjectRepository
import io.mockk.every
import org.junit.jupiter.api.Test
import io.mockk.verify
import org.junit.jupiter.api.Disabled
import planmate.domain.models.Project
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.usecase.exceptions.InvalidRoleException
import planmate.domain.usecase.exceptions.NameCantBeNullException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random
import org.junit.jupiter.api.assertThrows

class CreateProjectUseCaseTest {
    private var repository: ProjectRepository = mockk(relaxed = true)
    private lateinit var createProjectUseCase: CreateProjectUseCase

    @BeforeEach
    fun setup() {
        createProjectUseCase = CreateProjectUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should succeed when user is admin and name is not empty`() {
        // Given
        val user = User(id = "u1",name="Ala", role = Role.ADMIN)
        val project = Project(id = random(), name = "New Project")

        every { repository.createProject(project) } returns Unit

        // When
        createProjectUseCase.createProject(project, user)

        // Then
        verify(exactly = 1) { repository.createProject(project) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw InvalidRoleException when user is not admin`() {
        // Given
        val user = User(id = "1", name = "Ala", role = Role.MATE)
        val project = Project(id = random(), name = "New Project")

        // When & Then
        assertThrows<InvalidRoleException> {
            createProjectUseCase.createProject(project, user)
        }
    }
    @Disabled
    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should throw NameCantBeNullException when name is empty`() {
        // Given
        val user = User(id = "u3", name = "Ala", role = Role.ADMIN)
        val project = Project(id = random(), name = "")

        // When & Then
        assertThrows<NameCantBeNullException> {
            createProjectUseCase.createProject(project, user)
        }
    }
}