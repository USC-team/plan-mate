package planmate.domain.usecase

import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import planmate.domain.repository.ProjectRepository
import io.mockk.every
import org.junit.jupiter.api.Test
import io.mockk.verify
import planmate.domain.models.Project
import planmate.domain.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random
import planmate.domain.models.User.Role
import planmate.domain.usecase.projectsUseCases.CreateProjectUseCase

class CreateProjectUseCaseTest {
    private var projectRepository: ProjectRepository = mockk(relaxed = true)
    private lateinit var createProjectUseCase: CreateProjectUseCase

    @BeforeEach
    fun setup() {
        createProjectUseCase = CreateProjectUseCase(projectRepository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should succeed when user is admin and name is not empty`() {
        // Given
        val user = User(id = random(), name = "Ala", role = Role.ADMIN)
        val project = Project(id = random(), name = "New Project",userId= user.id)

        every { projectRepository.createProject(project) } returns Unit

        // When
        createProjectUseCase.createProject(project)

        // Then
        verify(exactly = 1) { projectRepository.createProject(project) }
    }

}