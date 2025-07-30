package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.StateDto
import planmate.data.repository.datasource.StatesDataSource
import kotlin.uuid.ExperimentalUuidApi

class StatesDataSourceImp(
    private val csvFileHandler: CsvFileHandler,
) : StatesDataSource {

    private val header = arrayOf("id", "name", "projectId")

    override suspend fun getAllStates(projectId: String): List<StateDto> {
        return csvFileHandler.readAllLines()
            .filter { cols ->
                cols.size >= header.size && cols[PROJECTID_INDEX] == projectId
            }
            .map { cols ->
                StateDto(
                    id = cols[ID_INDEX],
                    name = cols[NAME_INDEX],
                    projectId = cols[PROJECTID_INDEX]
                )
            }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createState(state: StateDto) {
        val stateRow = arrayOf(state.id, state.name, state.projectId)

        csvFileHandler.appendLine(headerColumns = header, newRow = stateRow)
    }

    override suspend fun updateState(state: StateDto) {
        csvFileHandler.updateLine(
            headerColumns = header,
            updatedRow = arrayOf(state.id, state.name, state.projectId)
        )
    }

    override suspend fun deleteState(stateId: String) {
        csvFileHandler.deleteLine(
            headerColumns = header,
            rowIdToDelete = stateId
        )
    }

    companion object {
        private const val ID_INDEX = 0
        private const val NAME_INDEX = 1
        private const val PROJECTID_INDEX = 2
    }
}