package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import planmate.domain.repository.StatesRepository
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random
import io.mockk.verify

class DeleteStateUseCaseTest {
    private var repository: StatesRepository = mockk(relaxed = true)
    private lateinit var deleteStateUseCase: DeleteStateUseCase

    @BeforeEach
    fun setup() {
        deleteStateUseCase = DeleteStateUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteState should delegate to repository`() {
        // Given
        val stateId = random()
        every { repository.deleteState(stateId) } returns Unit

        // When
        deleteStateUseCase(stateId)

        // Then
        verify(exactly = 1) { repository.deleteState(stateId) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteState should propagate exception if repository throws`() {
        // Given
        val stateId = random()
        val expectedException = IllegalStateException("Cannot delete state")
        every { repository.deleteState(stateId) } throws expectedException

        // When && Then
        try {
            deleteStateUseCase(stateId)
            throw AssertionError("Expected IllegalStateException but none was thrown")
        } catch (actual: IllegalStateException) {
            assert(actual.message == "Cannot delete state")
        }
    }
}