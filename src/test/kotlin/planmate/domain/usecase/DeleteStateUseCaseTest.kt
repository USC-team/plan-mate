package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import planmate.domain.repository.StatesRepository
import planmate.domain.usecase.statesUseCases.DeleteStateUseCase
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

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
        deleteStateUseCase.deleteState(stateId)

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
        assertThrows<Exception> {
            deleteStateUseCase.deleteState(stateId)
        }
    }
}