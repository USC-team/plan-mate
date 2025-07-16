package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository
import planmate.domain.usecase.tasksUseCases.UpdateTaskUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class UpdateTaskUseCaseTest {
    private var repository: TasksRepository = mockk(relaxed = true)
    private lateinit var updateTaskUseCase: UpdateTaskUseCase

    @BeforeEach
    fun setup() {
        updateTaskUseCase = UpdateTaskUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateTask should return created Task`() {
        // Given
        val projectId = random()
        val updatedTask = Task(
            id = random(),
            title = "task",
            description = "details",
            stateId = random(),
            projectId = projectId
        )

        every { repository.updateTask(updatedTask) } returns Unit

        // When
        updateTaskUseCase.updateTask(updatedTask)

        // Then
        verify(exactly = 1) { repository.updateTask(updatedTask) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `invoke should propagate exception if repository throws`() {
        // Given
        val invalidTask = Task(
            id = random(),
            title = " ",
            description = "details",
            stateId = random(),
            projectId = random()
        )
        val expectedException = Exception("title cannot be empty")

        every { repository.updateTask(invalidTask) } throws expectedException

        // When && Then
        assertThrows<Exception> {
            updateTaskUseCase.updateTask(invalidTask)
        }

    }
}