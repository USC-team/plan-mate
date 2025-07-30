package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.projectsUseCases.DeleteProjectUseCase
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
        runTest {
            // Given
            val projectId = random()

            coEvery { repository.deleteProject(projectId) } returns Unit

            // When
            deleteProjectUseCase.deleteProject(projectId)

            // Then
            coVerify(exactly = 1) { repository.deleteProject(projectId) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteProject should propagate exception if repository throws`() {
        runTest {
            // Given
            val projectId = random()

            coEvery { repository.deleteProject(projectId) } throws Exception()

            // When && Then
            assertThrows<Exception> {
                deleteProjectUseCase.deleteProject(projectId)
            }
        }
    }
}