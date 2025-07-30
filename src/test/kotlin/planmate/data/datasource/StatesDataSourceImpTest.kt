package planmate.data.datasource

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.StateDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class StatesDataSourceImpTest {
    private val mockCsvHandler = mockk<CsvFileHandler>(relaxed = true)
    private val dataSource = StatesDataSourceImp(mockCsvHandler)

    private val header = arrayOf("id", "name", "projectId")


    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllStates should returns correct DTO list when CSV contains matching projectId`() {
        runTest {
            // Given
            val projectId = "proj-1"
            val stateId1 = Uuid.toString()
            val stateId2 = Uuid.toString()
            val csvLines = listOf(
                header,
                arrayOf(stateId1, "ToDo", "proj-1"),
                arrayOf(stateId2, "Done", "proj-2")
            )
            every { mockCsvHandler.readAllLines() } returns csvLines

            // When
            val result = dataSource.getAllStates(projectId)

            // Then
            assertThat(result.map { it.projectId }).containsExactly(projectId)
        }
    }

    @Test
    fun `getAllStates returns empty when CSV is empty`() = runTest {
        // Given
        every { mockCsvHandler.readAllLines() } returns emptyList()

        // When
        val result = dataSource.getAllStates("any‑proj")

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getAllStates returns empty when only header present`() = runTest {
        // Given
        every { mockCsvHandler.readAllLines() } returns listOf(header)

        // When
        val result = dataSource.getAllStates("proj‑X")

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getAllStates ignores rows with insufficient columns`() = runTest {
        // Given
        val badLine = arrayOf("only", "two")
        every { mockCsvHandler.readAllLines() } returns listOf(header, badLine)

        // When
        val result = dataSource.getAllStates("proj‑1")

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getAllStates filters out rows with non‑matching projectId`() = runTest {
        // Given
        val row = arrayOf("id1", "SomeState", "other‑proj")
        every { mockCsvHandler.readAllLines() } returns listOf(header, row)

        // When
        val result = dataSource.getAllStates("proj‑1")

        // Then
        assertThat(result).isEmpty()
    }


    @Test
    fun `getAllStates propagates exception as Exception with message`() {
        runTest {
            // Given
            val projectId = "proj-xxx"
            every { mockCsvHandler.readAllLines() } throws RuntimeException("I/O error")

            // When & Then
            assertThrows<Exception> {
                dataSource.getAllStates(projectId)
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should appendLine when correct header and row`() {
        runTest {
            // Given
            val stateDto = StateDto(
                id = Uuid.toString(),
                name = "Review",
                projectId = "proj-42"
            )

            // When
            dataSource.createState(stateDto)

            // Then
            val expectedRow = arrayOf(stateDto.id, stateDto.name, stateDto.projectId)
            verify(exactly = 1) {
                mockCsvHandler.appendLine(
                    headerColumns = header,
                    newRow = expectedRow
                )
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState wraps underlying exception`() {
        runTest {
            // Given
            val stateDto = StateDto(
                id = Uuid.toString(),
                name = "Review",
                projectId = "proj-42"
            )
            every {
                mockCsvHandler.appendLine(
                    headerColumns = header,
                    newRow = arrayOf(stateDto.id, stateDto.name, stateDto.projectId)
                )
            } throws RuntimeException("Disk full")

            // When && Then
            assertThrows<Exception> { dataSource.createState(stateDto) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateState should updateLine with correct parameters`() {
        runTest {
            // Given
            val stateDto = StateDto(
                id = Uuid.toString(),
                name = "InProgress",
                projectId = "proj-99"
            )

            // When
            dataSource.updateState(stateDto)

            // Then
            val expectedUpdatedRow = arrayOf(stateDto.id, stateDto.name, stateDto.projectId)
            verify(exactly = 1) {
                mockCsvHandler.updateLine(
                    headerColumns = header,
                    updatedRow = expectedUpdatedRow
                )
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateState wraps underlying exception`() {
        runTest {
            // Given
            val stateDto = StateDto(
                id = Uuid.toString(),
                name = "InProgress",
                projectId = "proj-99"
            )
            every {
                mockCsvHandler.updateLine(
                    headerColumns = header,
                    updatedRow = arrayOf(stateDto.id, stateDto.name, stateDto.projectId)
                )
            } throws RuntimeException("Write error")

            // When && Then
            assertThrows<Exception> { dataSource.updateState(stateDto) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteState should deleteLine when correct parameters`() {
        runTest {
            // Given
            val stateId = Uuid.toString()

            // When
            dataSource.deleteState(stateId)

            // Then
            verify(exactly = 1) {
                mockCsvHandler.deleteLine(
                    headerColumns = header,
                    rowIdToDelete = stateId
                )
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteState wraps underlying exception`() {
        runTest {
            // Given
            val stateId = Uuid.toString()
            every {
                mockCsvHandler.deleteLine(
                    headerColumns = header,
                    rowIdToDelete = stateId
                )
            } throws RuntimeException("Delete failure")

            // When && Then
            assertThrows<Exception> { dataSource.deleteState(stateId) }
        }
    }
}