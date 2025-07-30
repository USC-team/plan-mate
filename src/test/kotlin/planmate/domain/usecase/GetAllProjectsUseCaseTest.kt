package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.Project
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class GetAllProjectsUseCaseTest {
    private var repository: ProjectRepository = mockk(relaxed = true)
    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase

    @BeforeEach
    fun setup() {
        getAllProjectsUseCase = GetAllProjectsUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllProjects should delegate to repository and return list of projects`() {
        runTest {
            // Given
            val userId = random();
            val expectedProjects = listOf(
                Project(random(), "Project1", userId),
                Project(random(), "Project2", userId),
                Project(random(), "Project3", userId)
            )

            coEvery { repository.getAllProjects() } returns expectedProjects

            // When
            val actual = getAllProjectsUseCase.getAllProjects()

            // Then
            assertEquals(expectedProjects, actual, "UseCase must return exactly what the repository returns")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllProjects should return empty list when repository returns empty`() {
        runTest {
            // Given
            coEvery { repository.getAllProjects() } returns emptyList()

            // When
            val actual = getAllProjectsUseCase.getAllProjects()

            // Then
            assertEquals(emptyList(), actual)
        }
    }
}