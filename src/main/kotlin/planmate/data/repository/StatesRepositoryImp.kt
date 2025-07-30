package planmate.data.repository

import planmate.data.mapper.fromDomain
import planmate.data.mapper.toDomain
import planmate.data.repository.datasource.StatesDataSource
import planmate.domain.models.State
import planmate.domain.repository.StatesRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class StatesRepositoryImp(
    private val statesDataSource: StatesDataSource,
) : StatesRepository {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getAllStates(projectId: Uuid): List<State> {
        return statesDataSource.getAllStates(projectId.toString())
            .map { it.toDomain() }
    }

    override suspend fun createState(state: State) {
        statesDataSource.createState(state.fromDomain(state))
    }

    override suspend fun updateState(state: State) {
        statesDataSource.updateState(state.fromDomain(state))
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteState(stateId: Uuid) {
        statesDataSource.deleteState(stateId.toString())
    }
}