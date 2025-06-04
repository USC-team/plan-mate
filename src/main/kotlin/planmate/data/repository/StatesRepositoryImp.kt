package planmate.data.repository

import planmate.data.csvHandler.CsvFileHandler
import planmate.domain.models.State
import planmate.domain.repository.StatesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class StatesRepositoryImp(
    private val csvFileHandler: CsvFileHandler,
) : StatesRepository {

    private val header = arrayOf("id", "name", "projectId")

    @OptIn(ExperimentalUuidApi::class)
    override fun getAllStates(projectId: Uuid): List<State> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createState(state: State) {
        val filename = "states_project_${state.projectId}.csv"

        val stateRow = arrayOf(state.id.toString(), state.name, state.projectId.toString())

        csvFileHandler.appendLine(headerColumns = header, newRow = stateRow)
    }

    override fun updateState(state: State) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteState(projectId: Uuid) {
        TODO("Not yet implemented")
    }
}