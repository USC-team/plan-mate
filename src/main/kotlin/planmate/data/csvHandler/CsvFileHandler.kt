package planmate.data.csvHandler

import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

class CsvFileHandler(private val fileName: String) {
    private val basePath: String="C:\\Users\\Alaa\\IdeaProjects\\plan-mate\\src\\main\\kotlin\\planmate\\data\\resources"

    init {
        val dir = File(basePath)
        if (!dir.exists()) dir.mkdirs()
    }

    fun readAllLines(): List<Array<String>> {
        val file = File(Paths.get(basePath, fileName).toString())
        if (!file.exists()) return emptyList()

        return file
            .readLines()
            .asSequence()
            .filter { it.isNotBlank() }
            .map { it.split(",").toTypedArray() }
            .toList()
    }

    private fun writeAllLines(
        headerColumns: Array<String>,
        rows: List<Array<String>>,
    ) {
        val filePath = Paths.get(basePath, fileName).toString()
        val file = File(filePath)

        FileWriter(file, false).use { writer ->
            writer.append(headerColumns.joinToString(",")).append("\n")
            rows.asSequence()
                .map { it.joinToString(",") }
                .forEach { line ->
                    writer.append(line).append("\n")
                }
        }
    }

    fun appendLine(
        headerColumns: Array<String>,
        newRow: Array<String>,
    ) {
        val filePath = Paths.get(basePath, fileName).toString()
        val file = File(filePath)

        if (!file.exists()) {
            writeAllLines(headerColumns, listOf(newRow))
        } else {
            FileWriter(file, true).use { writer ->
                writer.append(newRow.joinToString(",")).append("\n")
            }
        }
    }
    fun updateLine(updatedRow: Array<String>, headerColumns: Array<String>) {
        val predicate: (Array<String>) -> Boolean = { it[0] == updatedRow[0] }

        val allLines = readAllLines().drop(1)
        val newLines = allLines.map { row ->
            if (predicate(row)) updatedRow else row
        }

        writeAllLines(headerColumns, newLines)
    }

    fun deleteLine(rowIdToDelete: String, headerColumns: Array<String>) {
        val predicate: (Array<String>) -> Boolean = { it[0] == rowIdToDelete }

        val allLines = readAllLines().drop(1)
        val newLines = allLines.filterNot { row -> predicate(row) }

        writeAllLines(headerColumns, newLines)
    }
}