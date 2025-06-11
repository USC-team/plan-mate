package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.State
import planmate.domain.repository.StatesRepository
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
        // Given
        val projectId = random()
        val updatedState = State(id = random(), name = "UpdatedState", projectId = projectId)

        every { repository.updateState(updatedState) } returns Unit

        // When
        updateStateUseCase(updatedState)

        // Then
        verify(exactly = 1) { repository.updateState(updatedState) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `invoke should propagate exception if repository throws`() {
        // Given
        val invalidState = State(id = random(), name = "", projectId = random())
        val expectedException = IllegalArgumentException("Name cannot be empty")

        every { repository.updateState(invalidState) } throws expectedException

        // When && Then
        try {
            updateStateUseCase(invalidState)
            throw AssertionError("Expected IllegalArgumentException, but none was thrown")
        } catch (actual: IllegalArgumentException) {
            assertEquals("Name cannot be empty", actual.message)
        }

    }
}