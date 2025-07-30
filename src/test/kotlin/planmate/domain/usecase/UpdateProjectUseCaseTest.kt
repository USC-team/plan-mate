package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.Project
import planmate.domain.repository.ProjectRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random
import org.junit.jupiter.api.assertThrows
import planmate.domain.usecase.projectsUseCases.UpdateProjectUseCase

class UpdateProjectUseCaseTest {
    private var repository: ProjectRepository = mockk(relaxed = true)
    private lateinit var updateProjectsUseCase: UpdateProjectUseCase

    @BeforeEach
    fun setup() {
        updateProjectsUseCase = UpdateProjectUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateProject should update when repository updates successfully`() {
        runTest {
            // Given
            val project = Project(random(), "Project1", random())

            coEvery { repository.updateProject(project) } returns Unit

            // When
            updateProjectsUseCase.updateProject(project)

            // Then
            coVerify(exactly = 1) { repository.updateProject(project) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateProject should throw exception when repository throws`() {
        runTest {
            // Given
            val project = Project(random(), "Project1", random())

            coEvery { repository.updateProject(project) } throws Exception()

            // When && Then
            assertThrows<Exception> {
                updateProjectsUseCase.updateProject(project)
            }
        }
    }
}