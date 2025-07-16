package planmate.data.repository


import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.dto.TaskDto
import planmate.data.mapper.fromDomain
import planmate.data.mapper.toDomain
import planmate.data.repository.datasource.TasksDataSource
import planmate.domain.models.Task
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TasksRepositoryImpTest {
    private val mockTasksDataSource = mockk<TasksDataSource>(relaxed = true)
    private val repo = TasksRepositoryImp(mockTasksDataSource)

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllTasks should returns mapped domain tasks when data source provides DTOs`() {
        // Given
        val projectId = Uuid.random()
        val dto1 = TaskDto(
            id = UUID.randomUUID().toString(),
            title = "Task1",
            description = "Desc1",
            stateId = UUID.randomUUID().toString(),
            projectId = projectId.toString()
        )
        val dto2 = TaskDto(
            id = UUID.randomUUID().toString(),
            title = "Task2",
            description = "Desc2",
            stateId = UUID.randomUUID().toString(),
            projectId = projectId.toString()
        )
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } returns listOf(dto1, dto2)

        // When
        val result = repo.getAllTasks(projectId)

        // Then
        assertThat(result).containsExactly(dto1.toDomain(), dto2.toDomain())
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllTasks should wraps exception when data source fails`() {
        // Given
        val projectId = Uuid.random()
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } throws Exception("Error")

        // When && Then
        assertThrows<Exception> {
            repo.getAllTasks(projectId)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTasksByState should returns mapped domain tasks when data source provides DTOs`() {
        // Given
        val projectId = Uuid.random()
        val stateId = Uuid.random()
        val dto = TaskDto(
            id = UUID.randomUUID().toString(),
            title = "Task1",
            description = "Desc1",
            stateId = stateId.toString(),
            projectId = projectId.toString()
        )
        every {
            mockTasksDataSource.getTasksByState(projectId.toString(), stateId.toString())
        } returns listOf(dto)

        // When
        val result = repo.getTasksByState(projectId, stateId)

        // Then
        assertThat(result).containsExactly(dto.toDomain())
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTasksByState should wraps exception when data source fails`() {
        // Given
        val projectId = Uuid.random()
        val stateId = Uuid.random()
        every {
            mockTasksDataSource.getTasksByState(projectId.toString(), stateId.toString())
        } throws RuntimeException("I/O error")

        // When && Then
        assertThrows<Exception> {
            repo.getTasksByState(projectId, stateId)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should delegates to data source when no existing ID`() {
        // Given
        val projectId = Uuid.random()
        val taskId = Uuid.random()
        val task = Task(
            id = taskId,
            title = "NewTask",
            description = "Details",
            stateId = Uuid.random(),
            projectId = projectId
        )
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } returns emptyList()

        // When
        repo.createTask(task)

        // Then
        verify(exactly = 1) {
            mockTasksDataSource.createTask(
                match { dto ->
                    dto.id == taskId.toString() &&
                            dto.title == "NewTask" &&
                            dto.description == "Details" &&
                            dto.projectId == projectId.toString()
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should throws when title is blank`() {
        // Given
        val projectId = Uuid.random()
        val task = Task(
            id = Uuid.random(),
            title = "",
            description = "Desc",
            stateId = Uuid.random(),
            projectId = projectId
        )

        // When && Then
        assertThrows<Exception> {
            repo.createTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should throws when description is blank`() {
        // Given
        val projectId = Uuid.random()
        val task = Task(
            id = Uuid.random(),
            title = "T",
            description = "",
            stateId = Uuid.random(),
            projectId = projectId
        )

        // When && Then
        assertThrows<Exception> {
            repo.createTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should throws when ID already exists`() {
        val projectId = Uuid.random()
        val taskId = Uuid.random()
        val task = Task(
            id = taskId,
            title = "NewTask",
            description = "Details",
            stateId = Uuid.random(),
            projectId = projectId
        )
        val existingDto = task.fromDomain(task)
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } returns listOf(existingDto)

        // When && Then
        assertThrows<Exception> {
            repo.createTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createTask should wraps exception from data source`() {
        // Given
        val projectId = Uuid.random()
        val task = Task(
            id = Uuid.random(),
            title = "NewTask",
            description = "Details",
            stateId = Uuid.random(),
            projectId = projectId
        )
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } returns emptyList()
        every { mockTasksDataSource.createTask(any()) } throws Exception("Error")

        // When && Then
        assertThrows<Exception> {
            repo.createTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateTask should delegates to data source when exists`() {
        // Given
        val projectId = Uuid.random()
        val taskId = Uuid.random()
        val task = Task(
            id = taskId,
            title = "UpdateTask",
            description = "NewDesc",
            stateId = Uuid.random(),
            projectId = projectId
        )
        val existingDto = task.fromDomain(task)
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } returns listOf(existingDto)

        // When
        repo.updateTask(task)

        // Then
        verify(exactly = 1) {
            mockTasksDataSource.updateTask(
                match { dto ->
                    dto.id == taskId.toString() &&
                            dto.title == "UpdateTask" &&
                            dto.description == "NewDesc" &&
                            dto.projectId == projectId.toString()
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateTask should throws when title is blank`() {
        // Given
        val projectId = Uuid.random()
        val task = Task(
            id = Uuid.random(),
            title = "",
            description = "Desc",
            stateId = Uuid.random(),
            projectId = projectId
        )
        // When && Then
        assertThrows<Exception> {
            repo.updateTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateTask should throws when description is blank`() {
        // Given
        val projectId = Uuid.random()
        val task = Task(
            id = Uuid.random(),
            title = "T",
            description = "",
            stateId = Uuid.random(),
            projectId = projectId
        )

        // When && Then
        assertThrows<Exception> {
            repo.updateTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateTask should throws when task not found`() {
        // Given
        val projectId = Uuid.random()
        val task = Task(
            id = Uuid.random(),
            title = "T",
            description = "Desc",
            stateId = Uuid.random(),
            projectId = projectId
        )
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } returns emptyList()

        // When && Then
        assertThrows<Exception> {
            repo.updateTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateTask should wraps exception from data source`() {
        // Given
        val projectId = Uuid.random()
        val task = Task(
            id = Uuid.random(),
            title = "T",
            description = "Desc",
            stateId = Uuid.random(),
            projectId = projectId
        )
        every { mockTasksDataSource.getAllTasks(projectId.toString()) } returns listOf(task.fromDomain(task))
        every { mockTasksDataSource.updateTask(any()) } throws Exception("error")

        // When && Then
        assertThrows<Exception> {
            repo.updateTask(task)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteTask should delegates to data source`() {
        // Given
        val taskId = Uuid.random()

        // When
        repo.deleteTask(taskId)

        // Then
        verify(exactly = 1) {
            mockTasksDataSource.deleteTask(taskId.toString())
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteTask should wraps exception from data source`() {
        // Given
        val taskId = Uuid.random()
        every { mockTasksDataSource.deleteTask(taskId.toString()) } throws Exception("Delete failure")

        // When && Then
        assertThrows<Exception> {
            repo.deleteTask(taskId)
        }
    }
}