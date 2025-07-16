package planmate.data.csvHandler

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.io.File
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
        val filePath = tempDir.resolve(fileName)
        csvHandler = CsvFileHandler(filePath.toString())
    }

    // ─────────────────────── Init block ───────────────────────

    @Test
    fun `should create directories if not exist`() {
        val nestedPath = tempDir.resolve("nested/inner/test.csv")
        val handler = CsvFileHandler(nestedPath.toString())
        val row = arrayOf("1", "Task", "proj-1")
        handler.appendLine(header, row)
        assertEquals(2, handler.readAllLines().size)
    }

    @Test
    fun `should skip mkdirs if dir already exists`() {
        val dir = tempDir.resolve("existing").toFile().apply { mkdirs() }
        val file = File(dir, "file.csv")
        val handler = CsvFileHandler(file.absolutePath)
        handler.appendLine(header, arrayOf("1", "Test", "proj-1"))
        assertEquals(2, handler.readAllLines().size)
    }

    @Test
    fun `should handle file with null parent`() {
        val file = File("test_file.csv").apply { delete() }
        val handler = CsvFileHandler(file.absolutePath)
        handler.appendLine(header, arrayOf("1", "Test", "proj-1"))
        assertEquals(2, handler.readAllLines().size)
        file.delete()
    }

    // ─────────────────────── appendLine ───────────────────────

    @Test
    fun `appendLine should write file when not exists`() {
        csvHandler.appendLine(header, arrayOf("1", "Task", "proj-1"))
        assertEquals(2, csvHandler.readAllLines().size)
    }

    @Test
    fun `appendLine should handle empty existing file`() {
        File(tempDir.toFile(), fileName).writeText("")
        csvHandler.appendLine(header, arrayOf("1", "Task", "proj-1"))
        assertEquals(2, csvHandler.readAllLines().size)
    }

    @Test
    fun `appendLine should append to file with header and one row`() {
        val row1 = arrayOf("1", "Task", "proj-1")
        val row2 = arrayOf("2", "Done", "proj-1")
        csvHandler.appendLine(header, row1)
        csvHandler.appendLine(header, row2)
        val all = csvHandler.readAllLines()
        assertEquals(3, all.size)
        assertArrayEquals(row2, all[2])
    }

    @Test
    fun `appendLine should append to file created manually with header`() {
        val file = File(tempDir.toFile(), fileName)
        file.writeText("id,name,projectId\n1,Task,proj-1\n")
        csvHandler = CsvFileHandler(file.absolutePath)
        val row = arrayOf("2", "New", "proj-2")
        csvHandler.appendLine(header, row)
        val all = csvHandler.readAllLines()
        assertEquals(3, all.size)
        assertArrayEquals(row, all[2])
    }

    // ─────────────────────── updateLine ───────────────────────

    @Test
    fun `updateLine should update matching row`() {
        val row1 = arrayOf("1", "Task", "proj-1")
        val row2 = arrayOf("2", "Done", "proj-1")
        csvHandler.appendLine(header, row1)
        csvHandler.appendLine(header, row2)
        csvHandler.updateLine(arrayOf("1", "InProgress", "proj-2"), header)
        val lines = csvHandler.readAllLines()
        assertTrue(lines.any { it.contentEquals(arrayOf("1", "InProgress", "proj-2")) })
    }

    @Test
    fun `updateLine should update all matching rows`() {
        val file = File(tempDir.toFile(), fileName)
        file.writeText("id,name,projectId\n1,Task,proj-1\n1,Other,proj-x\n")
        csvHandler.updateLine(arrayOf("1", "Updated", "proj-z"), header)
        val lines = csvHandler.readAllLines().drop(1)
        assertTrue(lines.all { it[1] == "Updated" })
    }

    @Test
    fun `updateLine should ignore non-existing ID`() {
        val original = arrayOf("1", "Task", "proj-1")
        csvHandler.appendLine(header, original)
        csvHandler.updateLine(arrayOf("999", "Nothing", "proj-1"), header)
        val lines = csvHandler.readAllLines()
        assertArrayEquals(original, lines[1])
    }

    // ─────────────────────── deleteLine ───────────────────────

    @Test
    fun `deleteLine should delete matching row`() {
        val row1 = arrayOf("1", "Task", "proj-1")
        val row2 = arrayOf("2", "Done", "proj-1")
        csvHandler.appendLine(header, row1)
        csvHandler.appendLine(header, row2)
        csvHandler.deleteLine("2", header)
        val lines = csvHandler.readAllLines()
        assertFalse(lines.any { it[0] == "2" })
    }

    @Test
    fun `deleteLine should delete all matching rows`() {
        File(tempDir.toFile(), fileName).writeText("id,name,projectId\n1,Task,proj-1\n1,Another,proj-x\n")
        csvHandler.deleteLine("1", header)
        assertEquals(1, csvHandler.readAllLines().size) // header only
    }

    @Test
    fun `deleteLine should ignore non-existing ID`() {
        val row = arrayOf("1", "Task", "proj-1")
        csvHandler.appendLine(header, row)
        csvHandler.deleteLine("999", header)
        assertEquals(2, csvHandler.readAllLines().size)
    }

    // ─────────────────────── readAllLines ───────────────────────

    @Test
    fun `readAllLines should return empty list for missing file`() {
        val tempHandler = CsvFileHandler(tempDir.resolve("missing.csv").toString())
        assertTrue(tempHandler.readAllLines().isEmpty())
    }

    @Test
    fun `readAllLines should filter blank or whitespace lines`() {
        File(tempDir.toFile(), fileName).writeText("id,name,projectId\n\n \n2,Task,proj-1\n")
        val result = csvHandler.readAllLines()
        assertEquals(2, result.size)
    }

    @Test
    fun `readAllLines should parse malformed lines`() {
        File(tempDir.toFile(), fileName).writeText(
            "id,name,projectId\n1,OnlyOne\n2,Good,Row\n3,Too,many,fields\n"
        )
        val result = csvHandler.readAllLines()
        assertEquals(4, result.size)
        assertEquals(2, result[1].size)
        assertEquals(3, result[2].size)
        assertEquals(4, result[3].size)
    }

    // ─────────────────────── extra ───────────────────────

    @Test
    fun `constructor should not create directory if it already exists`() {
        val dir = tempDir.resolve("already_here").toFile().apply { mkdirs() }
        val file = File(dir, "file.csv")
        val handler = CsvFileHandler(file.absolutePath)
        val row = arrayOf("1", "ExistingDir", "proj-x")
        handler.appendLine(header, row)
        val lines = handler.readAllLines()
        assertEquals(2, lines.size)
        assertArrayEquals(row, lines[1])
    }

}
