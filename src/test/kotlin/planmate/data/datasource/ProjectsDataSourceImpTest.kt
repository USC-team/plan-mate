package planmate.data.datasource

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.ProjectDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProjectsDataSourceImpTest {

    private val mockCsvHandler = mockk<CsvFileHandler>(relaxed = true)
    private val dataSource = ProjectsDataSourceImp(mockCsvHandler)

    private val header = arrayOf("id", "name", "userId")

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllProjects should return correct DTO list when CSV contains projects`() {
        // Given
        val id1 = Uuid.random().toString()
        val id2 = Uuid.random().toString()
        val userId = Uuid.random().toString()

        val project1 = arrayOf(id1, "project1", userId)
        val project2 = arrayOf(id2, "project2", userId)
        val csvLines = listOf(header, project1, project2)

        every { mockCsvHandler.readAllLines() } returns csvLines

        val expected = listOf(
            ProjectDto(id1, "project1", userId),
            ProjectDto(id2, "project2", userId)
        )

        // When
        val result = dataSource.getAllProjects().drop(1).map { ProjectDto(it.id, it.name, it.userId) }

        // Then
        assertThat(result).containsExactlyElementsIn(expected)
    }

    @Test
    fun `getAllProjects throws IO exception when csv file is empty`() {
        every { mockCsvHandler.readAllLines() } throws RuntimeException("I/O error")

        assertThrows<Exception> {
            dataSource.getAllProjects()
        }
    }

    @Test
    fun `createProject should appendLine when correct header and row are passed`() {
        val projectDto = ProjectDto("id-123", "Project1", "user-456")

        dataSource.createProject(projectDto)

        val expectedRow = arrayOf(projectDto.id, projectDto.name, projectDto.userId)
        verify(exactly = 1) {
            mockCsvHandler.appendLine(
                headerColumns = header,
                newRow = expectedRow
            )
        }
    }

    @Test
    fun `createProject throws exception when the dist is full`() {
        val projectDto = ProjectDto("id-123", "Project1", "user-456")
        every {
            mockCsvHandler.appendLine(
                headerColumns = header,
                newRow = arrayOf(projectDto.id, projectDto.name, projectDto.userId)
            )
        } throws RuntimeException("Disk full")

        assertThrows<Exception> { dataSource.createProject(projectDto) }
    }

    @Test
    fun `updateProject should updateLine with correct parameters`() {
        val projectDto = ProjectDto("id-123", "Project1", "user-456")

        dataSource.updateProject(projectDto)

        val expectedRow = arrayOf(projectDto.id, projectDto.name, projectDto.userId)
        verify(exactly = 1) {
            mockCsvHandler.updateLine(
                headerColumns = header,
                updatedRow = expectedRow
            )
        }
    }

    @Test
    fun `updateProject throws exception when a write exception happens`() {
        val projectDto = ProjectDto("id-123", "Project1", "user-456")
        every {
            mockCsvHandler.updateLine(
                headerColumns = header,
                updatedRow = arrayOf(projectDto.id, projectDto.name, projectDto.userId)
            )
        } throws RuntimeException("Write error")

        assertThrows<Exception> { dataSource.updateProject(projectDto) }
    }

    @Test
    fun `deleteProject should deleteLine when correct parameters`() {
        val projectId = "id-123"

        dataSource.deleteProject(projectId)

        verify(exactly = 1) {
            mockCsvHandler.deleteLine(
                headerColumns = header,
                rowIdToDelete = projectId
            )
        }
    }

    @Test
    fun `deleteProject throws exception when delete fails`() {
        val projectId = "id-123"
        every {
            mockCsvHandler.deleteLine(
                headerColumns = header,
                rowIdToDelete = projectId
            )
        } throws RuntimeException("Delete failure")

        assertThrows<Exception> { dataSource.deleteProject(projectId) }
    }
}
