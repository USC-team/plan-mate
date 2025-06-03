package planmate.data.repositoryImp

import planmate.data.csvHandler.CsvFileHandler
import planmate.domain.repository.DeleteStateRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteStateRepositoryImp(
    private val csvFileHandler: CsvFileHandler,
) : DeleteStateRepository {
    @OptIn(ExperimentalUuidApi::class)
    override fun deleteState(stateId: Uuid, projectId: Uuid) {
        TODO("Not yet implemented")
    }
}