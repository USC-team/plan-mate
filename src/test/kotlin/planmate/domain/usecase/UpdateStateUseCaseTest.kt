package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.State
import planmate.domain.repository.StatesRepository
import planmate.domain.usecase.statesUseCases.UpdateStateUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class UpdateStateUseCaseTest {
    private var repository: StatesRepository = mockk(relaxed = true)
    private lateinit var updateStateUseCase: UpdateStateUseCase

    @BeforeEach
    fun setup() {
        updateStateUseCase = UpdateStateUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateState should return created state`() {
        runTest {
            // Given
            val projectId = random()
            val updatedState = State(id = random(), name = "UpdatedState", projectId = projectId)

            coEvery { repository.updateState(updatedState) } returns Unit

            // When
            updateStateUseCase.updateState(updatedState)

            // Then
            coVerify(exactly = 1) { repository.updateState(updatedState) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `invoke should propagate exception if repository throws`() {
        runTest {
            // Given
            val invalidState = State(id = random(), name = "", projectId = random())
            val expectedException = IllegalArgumentException("Name cannot be empty")

            coEvery { repository.updateState(invalidState) } throws expectedException

            // When && Then
            assertThrows<Exception> {
                updateStateUseCase.updateState(invalidState)
            }
        }

    }
}