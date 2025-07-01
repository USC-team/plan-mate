package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.TaskDto
import planmate.data.repository.datasource.TasksDataSource

class TasksDataSourceImp(
    private val csvFileHandler: CsvFileHandler
) : TasksDataSource {

    private val header = arrayOf("id", "title", "description", "stateId", "projectId")

    override fun getAllTasks(projectId: String): List<TaskDto>  {
        return csvFileHandler.readAllLines()
            .drop(1)
            .filter { cols ->
                cols.size >= header.size && cols[PROJECTID_INDEX] == projectId
            }
            .map { cols ->
                TaskDto(
                    id = cols[ID_INDEX],
                    title = cols[TITLE_INDEX],
                    description = cols[DESCRIPTION_INDEX],
                    stateId = cols[STATEID_INDEX],
                    projectId = cols[PROJECTID_INDEX]
                )
            }
    }

    override fun getTasksByState(projectId: String, stateId: String): List<TaskDto> {
        return csvFileHandler.readAllLines()
            .drop(1)
            .filter { cols ->
                cols.size >= header.size && cols[PROJECTID_INDEX] == projectId && cols[STATEID_INDEX] == stateId
            }
            .map { cols ->
                TaskDto(
                    id = cols[ID_INDEX],
                    title = cols[TITLE_INDEX],
                    description = cols[DESCRIPTION_INDEX],
                    stateId = cols[STATEID_INDEX],
                    projectId = cols[PROJECTID_INDEX]
                )
            }
    }

    override fun createTask(task: TaskDto) {
        val row = arrayOf(task.id, task.title, task.description, task.stateId, task.projectId)
        csvFileHandler.appendLine(headerColumns = header, newRow = row)
    }

    override fun updateTask(task: TaskDto) {
        val updatedRow = arrayOf(task.id, task.title, task.description, task.stateId, task.projectId)
        csvFileHandler.updateLine(headerColumns = header, updatedRow = updatedRow)
    }

    override fun deleteTask(taskId: String) {
        csvFileHandler.deleteLine(headerColumns = header, rowIdToDelete = taskId)
    }

    companion object{
        private const val ID_INDEX=0
        private const val TITLE_INDEX=1
        private const val DESCRIPTION_INDEX=2
        private const val STATEID_INDEX=3
        private const val PROJECTID_INDEX=4
    }
}
