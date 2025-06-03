package planmate.data.repositoryImp

import planmate.data.csvHandler.CsvFileHandler
import planmate.domain.models.State
import planmate.domain.repository.CreateStateRepository
import kotlin.uuid.ExperimentalUuidApi

class CreateStateRepositoryImp(
    private val csvFileHandler: CsvFileHandler,
) : CreateStateRepository {
    private val header = arrayOf("id", "name", "projectId")

    @OptIn(ExperimentalUuidApi::class)
    override fun createState(state: State) {
        val filename = "states_project_${state.projectId}.csv"

        val stateRow = arrayOf(state.id.toString(), state.name, state.projectId.toString())

        csvFileHandler.appendLine(filename = filename, headerColumns = header, newRow = stateRow)
    }
}