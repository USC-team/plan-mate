package planmate.domain.usecase


import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository
import planmate.domain.usecase.tasksUseCases.CreateTaskUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class CreateTaskUseCaseTest {

    private var repository: TasksRepository = mockk(relaxed = true)
    private lateinit var createTasksUseCase: CreateTaskUseCase

    @BeforeEach
    fun setup() {
        createTasksUseCase = CreateTaskUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should return created Task`() {
        // Given
        val projectId = random()
        val task = Task(
            id = random(),
            title = "NewTask",
            description = "details",
            stateId = random(),
            projectId = projectId
        )

        every { repository.createTask(task) } returns Unit

        // When
        createTasksUseCase.createTask(task)

        // Then
        verify(exactly = 1) { repository.createTask(task) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should propagate exception when Title empty`() {
        // Given
        val invalidTask = Task(
            id = random(),
            title = "",
            description = "details",
            stateId = random(),
            projectId = random()
        )
        every { repository.createTask(invalidTask) } throws Exception("Title cannot be empty")

        // When && Then
        assertThrows<Exception> {
            createTasksUseCase.createTask(invalidTask)
        }

    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should propagate exception when description empty`() {
        // Given
        val invalidTask = Task(
            id = random(),
            title = "NewTask",
            description = "",
            stateId = random(),
            projectId = random()
        )
        every { repository.createTask(invalidTask) } throws Exception("Description cannot be empty")

        // When && Then
        assertThrows<Exception> {
            createTasksUseCase.createTask(invalidTask)
        }

    }
}