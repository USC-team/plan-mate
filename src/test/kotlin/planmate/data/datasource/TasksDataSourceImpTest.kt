package planmate.data.datasource

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.TaskDto
import java.util.*

class TasksDataSourceImpTest {

    private val mockCsvHandler = mockk<CsvFileHandler>(relaxed = true)
    private val dataSource = TasksDataSourceImp(mockCsvHandler)

    private val header = arrayOf("id", "title", "description", "userId", "stateId", "projectId")


    @Test
    fun `getAllTasks should returns only rows matching projectId`() {
        // Given
        val projectId = "proj-123"
        val taskId1 = UUID.randomUUID().toString()
        val taskId2 = UUID.randomUUID().toString()
        val csvLines: List<Array<String>> = listOf(
            header,
            arrayOf(taskId1, "T1", "Desc1", "userA", "stateX", projectId),
            arrayOf(taskId2, "T2", "Desc2", "userB", "stateY", "other-proj")
        )
        every { mockCsvHandler.readAllLines() } returns csvLines

        // When
        val result = dataSource.getAllTasks(projectId)

        // Then
        assertThat(result.map { it.id }).containsExactly(taskId1)
    }

    @Test
    fun `getAllTasks should wraps exception from readAllLines`() {
        // Given
        val projectId = "proj-xyz"
        every { mockCsvHandler.readAllLines() } throws RuntimeException("I/O error")

        // When && Then
        assertThrows<Exception> {
            dataSource.getAllTasks(projectId)
        }
    }

    @Test
    fun `getTasksByState should returns only rows matching projectId and stateId`() {
        val projectId = "proj-123"
        val stateMatch = "stateA"
        val stateOther = "stateB"
        val taskId1 = UUID.randomUUID().toString()
        val taskId2 = UUID.randomUUID().toString()
        val taskId3 = UUID.randomUUID().toString()
        val csvLines: List<Array<String>> = listOf(
            header,
            arrayOf(taskId1, "T1", "Desc1", "user1", stateMatch, projectId),
            arrayOf(taskId2, "T2", "Desc2", "user2", stateOther, projectId),
            arrayOf(taskId3, "T3", "Desc3", "user3", stateMatch, "other-proj")
        )
        every { mockCsvHandler.readAllLines() } returns csvLines

        val result = dataSource.getTasksByState(projectId, stateMatch)

        assertEquals(1, result.size)
        assertEquals(projectId, result.first().projectId)

    }

    @Test
    fun `getTasksByState should wraps exception from readAllLines`() {
        // Given
        val projectId = "proj-abc"
        val stateId = "stateX"
        every { mockCsvHandler.readAllLines() } throws RuntimeException("I/O error")

        // When && Then
        assertThrows<Exception> {
            dataSource.getTasksByState(projectId, stateId)
        }
    }


    @Test
    fun `createTask should appendLine when correct header and row`() {
        val dto = TaskDto(
            id = UUID.randomUUID().toString(),
            title = "Fix bug",
            description = "Details",
            userId = "user1",
            stateId = "state1",
            projectId = "proj-42"
        )
        dataSource.createTask(dto)
        val expectedRow = arrayOf(dto.id, dto.title, dto.description, dto.userId, dto.stateId, dto.projectId)
        verify(exactly = 1) {
            mockCsvHandler.appendLine(headerColumns = header, newRow = expectedRow)
        }
    }

    @Test
    fun `createTask should wraps underlying exception`() {
        // Given
        val dto = TaskDto(
            id = UUID.randomUUID().toString(),
            title = "Fix bug",
            description = "Details",
            userId = "user1",
            stateId = "state1",
            projectId = "proj-42"
        )
        every {
            mockCsvHandler.appendLine(
                headerColumns = header,
                newRow = arrayOf(dto.id, dto.title, dto.description, dto.userId, dto.stateId, dto.projectId)
            )
        } throws Exception("Error")

        // When && Then
        assertThrows<Exception> {
            dataSource.createTask(dto)
        }
    }

    @Test
    fun `updateTask should updateLine with correct parameters`() {
        // Given
        val taskDto = TaskDto(
            id = UUID.randomUUID().toString(),
            title = "InProgress",
            description = "Working",
            userId = "user2",
            stateId = "state2",
            projectId = "proj-99"
        )

        // When
        dataSource.updateTask(taskDto)
        val expectedRow =
            arrayOf(taskDto.id, taskDto.title, taskDto.description, taskDto.userId, taskDto.stateId, taskDto.projectId)

        // Then
        verify(exactly = 1) {
            mockCsvHandler.updateLine(headerColumns = header, updatedRow = expectedRow)
        }
    }

    @Test
    fun `updateTask should wraps underlying exception`() {
        // Given
        val taskDto = TaskDto(
            id = UUID.randomUUID().toString(),
            title = "InProgress",
            description = "Working",
            userId = "user2",
            stateId = "state2",
            projectId = "proj-99"
        )
        every {
            mockCsvHandler.updateLine(
                headerColumns = header,
                updatedRow = arrayOf(
                    taskDto.id,
                    taskDto.title,
                    taskDto.description,
                    taskDto.userId,
                    taskDto.stateId,
                    taskDto.projectId
                )
            )
        } throws Exception("Error")

        // When && Then
        assertThrows<Exception> {
            dataSource.updateTask(taskDto)
        }
    }

    @Test
    fun `deleteTask should deleteLine with correct parameters`() {
        // Given
        val taskId = UUID.randomUUID().toString()

        // When
        dataSource.deleteTask(taskId)

        // Then
        verify(exactly = 1) {
            mockCsvHandler.deleteLine(headerColumns = header, rowIdToDelete = taskId)
        }
    }

    @Test
    fun `deleteTask should wraps underlying exception`() {
        // Given
        val taskId = UUID.randomUUID().toString()
        every {
            mockCsvHandler.deleteLine(headerColumns = header, rowIdToDelete = taskId)
        } throws Exception("Delete failure")

        // When && Then
        assertThrows<Exception> {
            dataSource.deleteTask(taskId)
        }
    }

}