package planmate.data.csvHandler

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.test.Test

class CsvFileHandlerTest {

    @TempDir
    lateinit var tempDir: Path

    private val fileName = "test.csv"
    private val header = arrayOf("id", "name", "projectId")
    private lateinit var csvHandler: CsvFileHandler

    @BeforeEach
    fun setup() {
        csvHandler = CsvFileHandler(basePath = tempDir.toString(), fileName = fileName)
    }

    @Test
    fun `readAllLines should return empty list when file not exists`() {
        // Given && When
        val allLines = csvHandler.readAllLines()

        // Then
        assertTrue(allLines.isEmpty(), "Expected readAllLines() to return empty when file is missing")
    }

    @Test
    fun `appendLine should creates file and writes header plus one row`() {
        // Given
        val row = arrayOf("1", "ToDo", "proj-1")

        // When
        csvHandler.appendLine(headerColumns = header, newRow = row)

        // Then
        val allLines = csvHandler.readAllLines()
        assertArrayEquals(row, allLines[1], "Second line should be the appended row")
    }

    @Test
    fun `appendLine should appends multiple rows on existing file`() {
        // Given
        val row1 = arrayOf("1", "ToDo", "proj-1")
        val row2 = arrayOf("2", "InProgress", "proj-1")

        // When
        csvHandler.appendLine(headerColumns = header, newRow = row1)
        csvHandler.appendLine(headerColumns = header, newRow = row2)

        // Then
        val allLines = csvHandler.readAllLines()
        assertEquals(3, allLines.size, "Expected header + two data rows")
    }

    @Test
    fun `updateLine should replaces only the matching row and preserves others`() {
        //Given
        val row1 = arrayOf("1", "ToDo", "proj-1")
        val row2 = arrayOf("2", "Done", "proj-1")
        csvHandler.appendLine(headerColumns = header, newRow = row1)
        csvHandler.appendLine(headerColumns = header, newRow = row2)

        // When
        val updatedRow1 = arrayOf("1", "InReview", "proj-1")
        csvHandler.updateLine(updatedRow = updatedRow1, headerColumns = header)

        // Then
        val allLines = csvHandler.readAllLines()
        val dataLines = allLines.drop(1)
        assertTrue(
            dataLines.any { it.contentEquals(updatedRow1) },
            "Expected updated row1 to be present"
        )
    }

    @Test
    fun `deleteLine should removes only the specified row and keeps the rest`() {
        // Given
        val row1 = arrayOf("1", "ToDo", "proj-1")
        val row2 = arrayOf("2", "InProgress", "proj-1")
        val row3 = arrayOf("3", "Done", "proj-1")
        csvHandler.appendLine(headerColumns = header, newRow = row1)
        csvHandler.appendLine(headerColumns = header, newRow = row2)
        csvHandler.appendLine(headerColumns = header, newRow = row3)

        // When
        csvHandler.deleteLine(rowIdToDelete = "2", headerColumns = header)

        // Then
        val allLines = csvHandler.readAllLines()
        val dataLines = allLines.drop(1)
        // row2 should be gone
        assertFalse(
            dataLines.any { it.contentEquals(row2) },
            "Expected row2 to be deleted"
        )
    }
}