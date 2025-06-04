package planmate.data.csvHandler

import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

class CsvFileHandler(private val basePath: String) {

    init {
        val dir = File(basePath)
        if (!dir.exists()) dir.mkdirs()
    }

    fun readAllLines(filename: String): List<Array<String>> {
        val file = File(Paths.get(basePath, filename).toString())
        if (!file.exists()) return emptyList()

        return file
            .readLines()
            .asSequence()
            .filter { it.isNotBlank() }
            .map { it.split(",").toTypedArray() }
            .toList()
    }

    private fun writeAllLines(
        filename: String,
        headerColumns: Array<String>,
        rows: List<Array<String>>,
    ) {
        val filePath = Paths.get(basePath, filename).toString()
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
        filename: String,
        headerColumns: Array<String>,
        newRow: Array<String>,
    ) {
        val filePath = Paths.get(basePath, filename).toString()
        val file = File(filePath)

        if (!file.exists()) {
            writeAllLines(filename, headerColumns, listOf(newRow))
        } else {
            FileWriter(file, true).use { writer ->
                writer.append(newRow.joinToString(",")).append("\n")
            }
        }
    }
}