package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.State
import planmate.domain.repository.StatesRepository
import io.mockk.verify
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class CreateStateUseCaseTest {
    private var repository: StatesRepository = mockk(relaxed = true)
    private lateinit var createStateUseCase: CreateStateUseCase

    @BeforeEach
    fun setup() {
        createStateUseCase = CreateStateUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should return created state`() {
        // Given
        val projectId = random()
        val state = State(id = random(), name = "NewState", projectId = projectId)

        every { repository.createState(state) } returns Unit

        // When
        createStateUseCase(state)

        // Then
        verify(exactly = 1) { repository.createState(state) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should propagate exception when repository throws`() {
        // Given
        val invalidState = State(id = random(), name = "", projectId = random())
        val exception = IllegalArgumentException("Name cannot be empty")
        every { repository.createState(invalidState) } throws exception

        // When && Then
        try {
            createStateUseCase(invalidState)
            throw AssertionError("Expected IllegalArgumentException but none was thrown")
        } catch (e: IllegalArgumentException) {
            assert(e.message == "Name cannot be empty")
        }
    }
}