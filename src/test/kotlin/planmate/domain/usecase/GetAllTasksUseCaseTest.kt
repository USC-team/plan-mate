package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.Task
import planmate.domain.repository.TasksRepository
import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class GetAllTasksUseCaseTest {
    private var repository: TasksRepository = mockk(relaxed = true)
    private lateinit var getAllTasksUseCase: GetAllTasksUseCase

    @BeforeEach
    fun setup() {
        getAllTasksUseCase = GetAllTasksUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `invoke should delegate to repository and return list of tasks`() {
        runTest {
            // Given
            val projectId = random()
            val expectedTasks = listOf(
                Task(
                    id = random(),
                    title = "task1",
                    description = "details",
                    stateId = random(),
                    projectId = projectId
                ),
                Task(
                    id = random(),
                    title = "task2",
                    description = "details",
                    stateId = random(),
                    projectId = projectId
                ),
                Task(
                    id = random(),
                    title = "task3",
                    description = "details",
                    stateId = random(),
                    projectId = projectId
                )
            )

            coEvery { repository.getAllTasks(projectId) } returns expectedTasks

            // When
            val actual = getAllTasksUseCase.getAllTasks(projectId)

            // Then
            assertEquals(expectedTasks, actual, "UseCase must return exactly what the repository returns")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllTasks should return empty list when repository returns empty`() {
        runTest {
            // Given
            val projectId = random()
            coEvery { repository.getAllTasks(projectId) } returns emptyList()

            // When
            val actual = getAllTasksUseCase.getAllTasks(projectId)

            // Then
            assertEquals(emptyList(), actual)
        }
    }
}