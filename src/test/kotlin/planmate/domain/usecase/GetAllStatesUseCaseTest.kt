package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.State
import planmate.domain.repository.StatesRepository
import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class GetAllStatesUseCaseTest {
    private var repository: StatesRepository = mockk(relaxed = true)
    private lateinit var getAllStatesUseCase: GetAllStatesUseCase

    @BeforeEach
    fun setup() {
        getAllStatesUseCase = GetAllStatesUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `invoke should delegate to repository and return list of states`() {
        runTest {
            // Given
            val projectId = random()
            val expectedStates = listOf(
                State(random(), "Draft", projectId),
                State(random(), "InProgress", projectId),
                State(random(), "Completed", projectId)
            )

            coEvery { repository.getAllStates(projectId) } returns expectedStates

            // When
            val actual = getAllStatesUseCase.getAllStates(projectId)

            // Then
            assertEquals(expectedStates, actual, "UseCase must return exactly what the repository returns")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllStats should return empty list when repository returns empty`() {
        runTest {
            // Given
            val projectId = random()
            coEvery { repository.getAllStates(projectId) } returns emptyList()

            // When
            val actual = getAllStatesUseCase.getAllStates(projectId)

            // Then
            assertEquals(emptyList(), actual)
        }
    }
}