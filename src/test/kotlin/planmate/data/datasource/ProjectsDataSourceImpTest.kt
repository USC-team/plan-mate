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

    private val header = arrayOf("id", "name")

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllProjects should return correct DTO list when CSV contains projects`() {
        //Given
        val id1 = Uuid.random().toString()
        val id2 = Uuid.random().toString()

        val project1 = arrayOf(id1, "project1")
        val project2 = arrayOf(id2, "project2")
        val csvLines = listOf(header, project1, project2)

        every { mockCsvHandler.readAllLines() } returns csvLines

        val expected = listOf(
            ProjectDto(id1, "project1"),
            ProjectDto(id2, "project2")
        )

        //When
        val result = dataSource.getAllProjects().drop(1).map { ProjectDto(it.id, it.name) }

        //Then
        assertThat(result).containsExactlyElementsIn(expected)
    }

    @Test
    fun `getAllProjects throws IO exception when csv file is empty`() {
        // Given
        every { mockCsvHandler.readAllLines() } throws RuntimeException("I/O error")

        // When & Then
        assertThrows<Exception> {
            dataSource.getAllProjects()
        }
    }

    @Test
    fun `createProject should appendLine when correct header and row are passed`() {
        // Given
        val projectDto = ProjectDto(
            toString(),
            "Project1"
        )

        // When
        dataSource.createProject(projectDto)

        // Then
        val expectedRow = arrayOf(projectDto.id, projectDto.name)
        verify(exactly = 1) {
            mockCsvHandler.appendLine(
                headerColumns = header,
                newRow = expectedRow
            )
        }
    }

    @Test
    fun `createProject throws exception when the dist is full`() {
        // Given
        val projectDto = ProjectDto(
            toString(),
            "Project1"
        )
        every {
            mockCsvHandler.appendLine(
                headerColumns = header,
                newRow = arrayOf(projectDto.id, projectDto.name)
            )
        } throws RuntimeException("Disk full")

        // When && Then
        assertThrows<Exception> { dataSource.createProject(projectDto) }
    }

    @Test
    fun `updateProject should updateLine with correct parameters`() {
        // Given
        val projectDto = ProjectDto(
            toString(),
            "Project1"
        )

        // When
        dataSource.updateProject(projectDto)

        // Then
        val expectedUpdatedRow = arrayOf(projectDto.id, projectDto.name)
        verify(exactly = 1) {
            mockCsvHandler.updateLine(
                headerColumns = header,
                updatedRow = expectedUpdatedRow
            )
        }
    }

    @Test
    fun `updateProject throws exception when a write exception happens`() {
        // Given
        val projectDto = ProjectDto(
            toString(),
            "Project1"
        )
        every {
            mockCsvHandler.updateLine(
                headerColumns = header,
                updatedRow = arrayOf(projectDto.id, projectDto.name)
            )
        } throws RuntimeException("Write error")

        // When && Then
        assertThrows<Exception> { dataSource.updateProject(projectDto) }
    }

    @Test
    fun `deleteProject should deleteLine when correct parameters`() {
        // Given
        val projectId = toString()

        // When
        dataSource.deleteProject(projectId)

        // Then
        verify(exactly = 1) {
            mockCsvHandler.deleteLine(
                headerColumns = header,
                rowIdToDelete = projectId
            )
        }
    }

    @Test
    fun `deleteProject throws exception when delete fails`() {
        // Given
        val projectId = toString()
        every {
            mockCsvHandler.deleteLine(
                headerColumns = header,
                rowIdToDelete = projectId
            )
        } throws RuntimeException("Delete failure")

        // When && Then
        assertThrows<Exception> { dataSource.deleteProject(projectId) }
    }

}