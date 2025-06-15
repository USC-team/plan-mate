package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.repository.TasksRepository
import planmate.domain.usecase.tasksUseCases.DeleteTaskUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random


class DeleteTaskUseCaseTest {
    private var repository: TasksRepository = mockk(relaxed = true)
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup() {
        deleteTaskUseCase = DeleteTaskUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteTask should delegate to repository`() {
        // Given
        val taskId = random()
        every { repository.deleteTask(taskId) } returns Unit

        // When
        deleteTaskUseCase(taskId)

        // Then
        verify(exactly = 1) { repository.deleteTask(taskId) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteTask should propagate exception if repository throws`() {
        // Given
        val taskId = random()
        val expectedException = IllegalStateException("Cannot delete task")
        every { repository.deleteTask(taskId) } throws expectedException

        // When && Then
        assertThrows<Exception> {
            deleteTaskUseCase(taskId)
        }
    }
}