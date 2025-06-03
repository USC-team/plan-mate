package planmate.data.repositoryImp

import planmate.data.csvHandler.CsvFileHandler
import planmate.domain.models.State
import planmate.domain.repository.UpdateStateRepository

class UpdateStateRepositoryImp(
    private val csvFileHandler: CsvFileHandler,
) : UpdateStateRepository {
    override fun updateState(state: State) {
        TODO("Not yet implemented")
    }
}