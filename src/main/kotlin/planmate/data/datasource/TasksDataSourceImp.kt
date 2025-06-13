package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.TaskDto
import planmate.data.repository.datasource.TasksDataSource

class TasksDataSourceImp(
    private val csvFileHandler: CsvFileHandler
) : TasksDataSource {

    private val header = arrayOf("id", "title", "description", "userId", "stateId", "projectId")

    override fun getAllTasks(projectId: String): List<TaskDto> = try {
        csvFileHandler.readAllLines()
            .drop(1)
            .filter { cols ->
                cols.size >= 6 && cols[5] == projectId
            }
            .map { cols ->
                TaskDto(
                    id = cols[0],
                    title = cols[1],
                    description = cols[2],
                    userId = cols[3],
                    stateId = cols[4],
                    projectId = cols[5]
                )
            }
    } catch (e: Exception) {
        throw Exception("Failed to read tasks CSV for project $projectId", e)
    }

    override fun getTasksByState(projectId: String, stateId: String): List<TaskDto> = try {
        csvFileHandler.readAllLines()
            .drop(1)
            .filter { cols ->
                cols.size >= 6 && cols[5] == projectId && cols[4] == stateId
            }
            .map { cols ->
                TaskDto(
                    id = cols[0],
                    title = cols[1],
                    description = cols[2],
                    userId = cols[3],
                    stateId = cols[4],
                    projectId = cols[5]
                )
            }
    } catch (e: Exception) {
        throw Exception("Failed to read tasks CSV for project $projectId and state $stateId", e)
    }

    override fun createTask(task: TaskDto) {
        try {
            val row = arrayOf(task.id, task.title, task.description, task.userId, task.stateId, task.projectId)
            csvFileHandler.appendLine(headerColumns = header, newRow = row)
        } catch (e: Exception) {
            throw Exception("Failed to append new Task '${task.id}' to CSV for project ${task.projectId}", e)
        }
    }

    override fun updateTask(task: TaskDto) {
        try {
            val updatedRow = arrayOf(task.id, task.title, task.description, task.userId, task.stateId, task.projectId)
            csvFileHandler.updateLine(headerColumns = header, updatedRow = updatedRow)
        } catch (e: Exception) {
            throw Exception("Failed to update Task '${task.id}' in CSV for project ${task.projectId}", e)
        }
    }

    override fun deleteTask(taskId: String) {
        try {
            csvFileHandler.deleteLine(headerColumns = header, rowIdToDelete = taskId)
        } catch (e: Exception) {
            throw Exception("Failed to delete Task '$taskId' from CSV", e)
        }
    }
}
