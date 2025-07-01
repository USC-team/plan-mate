package planmate.data.repository

import planmate.data.dto.StateDto
import planmate.data.repository.datasource.StatesDataSource
import planmate.domain.models.State
import planmate.domain.repository.StatesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class StatesRepositoryImp(
    private val statesDataSource: StatesDataSource,
) : StatesRepository {

    @OptIn(ExperimentalUuidApi::class)
    override fun getAllStates(projectId: Uuid): List<State> {
        return statesDataSource.getAllStates(projectId.toString())
            .map { it.toDomain() }
    }

    override fun createState(state: State) {
        statesDataSource.createState(StateDto.fromDomain(state))
    }

    override fun updateState(state: State) {
        statesDataSource.updateState(StateDto.fromDomain(state))
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteState(stateId: Uuid) {
        statesDataSource.deleteState(stateId.toString())
    }
}