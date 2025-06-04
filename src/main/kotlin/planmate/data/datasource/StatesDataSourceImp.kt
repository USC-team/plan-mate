package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.StateDto
import planmate.data.repository.datasource.StatesDataSource
import kotlin.uuid.ExperimentalUuidApi

class StatesDataSourceImp(
    private val csvFileHandler: CsvFileHandler,
) : StatesDataSource {

    private val header = arrayOf("id", "name", "projectId")

    override fun getAllStates(projectId: String): List<StateDto> {
        return csvFileHandler.readAllLines()
            .drop(1)
            .filter { it.contains(projectId) }
            .map { cols ->
                StateDto(
                    id = cols[0],
                    name = cols[1],
                    projectId = cols[2]
                )
            }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createState(state: StateDto) {

        val filename = "states_project_${state.projectId}.csv"

        val stateRow = arrayOf(state.id, state.name, state.projectId)

        csvFileHandler.appendLine(headerColumns = header, newRow = stateRow)
    }

    override fun updateState(state: StateDto) {
        csvFileHandler.updateLine(
            headerColumns = arrayOf("id", "name", "projectId"),
            updatedRow = arrayOf(state.id, state.name, state.projectId)
        )
    }

    override fun deleteState(stateId: String) {
        csvFileHandler.deleteLine(
            headerColumns = arrayOf("id", "name", "projectId"),
            rowIdToDelete = stateId
        )
    }
}