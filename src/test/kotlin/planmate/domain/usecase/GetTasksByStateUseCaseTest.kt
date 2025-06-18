package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository
import planmate.domain.usecase.tasksUseCases.GetTasksByStateUseCase
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class GetTasksByStateUseCaseTest {
    private var repository: TasksRepository = mockk(relaxed = true)
    private lateinit var getAllTasksUseCase: GetTasksByStateUseCase

    @BeforeEach
    fun setup() {
        getAllTasksUseCase = GetTasksByStateUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `invoke should delegate to repository and return list of tasks`() {
        // Given
        val projectId = random()
        val stateId = random()
        val expectedTasks = listOf(
            Task(
                id = random(),
                title = "task1",
                description = "details",
                stateId = stateId,
                projectId = projectId
            ),
            Task(
                id = random(),
                title = "task2",
                description = "details",
                stateId = stateId,
                projectId = projectId
            ),
            Task(
                id = random(),
                title = "task3",
                description = "details",
                stateId = stateId,
                projectId = projectId
            )
        )

        every { repository.getTasksByState(projectId, stateId) } returns expectedTasks

        // When
        val actual = getAllTasksUseCase(projectId,stateId)

        // Then
        assertEquals(expectedTasks, actual, "UseCase must return exactly what the repository returns")
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllTasks should return empty list when repository returns empty`() {
        // Given
        val projectId = random()
        val stateId = random()
        every { repository.getTasksByState(projectId, stateId) } returns emptyList()

        // When
        val actual = getAllTasksUseCase(projectId,stateId)

        // Then
        assertEquals(emptyList(), actual)
    }

}