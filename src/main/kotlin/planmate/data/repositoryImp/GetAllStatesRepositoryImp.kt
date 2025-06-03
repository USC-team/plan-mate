package planmate.data.repositoryImp

import planmate.data.csvHandler.CsvFileHandler
import planmate.domain.models.State
import planmate.domain.repository.GetAllStatesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetAllStatesRepositoryImp(
    private val csvFileHandler: CsvFileHandler,
) : GetAllStatesRepository {
    @OptIn(ExperimentalUuidApi::class)
    override fun getAllStates(projectId: Uuid): List<State> {
        TODO("Not yet implemented")
    }
}