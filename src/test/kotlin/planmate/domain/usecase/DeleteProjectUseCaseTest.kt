package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.repository.ProjectRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class DeleteProjectUseCaseTest {
    private var repository: ProjectRepository = mockk(relaxed = true)
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase

    @BeforeEach
    fun setup() {
        deleteProjectUseCase = DeleteProjectUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteProject should delegate to repository`() {
        // Given
        val projectId = random()
        
        every { repository.deleteProject(projectId) } returns Unit

        // When
        deleteProjectUseCase.deleteProject(projectId)

        // Then
        verify(exactly = 1) { repository.deleteProject(projectId) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteProject should propagate exception if repository throws`() {
        // Given
        val projectId = random()

        every { repository.deleteProject(projectId) } throws Exception()

        // When && Then
        assertThrows<Exception> {
            deleteProjectUseCase.deleteProject(projectId)
        }
    }
}