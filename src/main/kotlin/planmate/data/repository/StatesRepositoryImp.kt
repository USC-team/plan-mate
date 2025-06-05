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
        return try {
            statesDataSource.getAllStates(projectId.toString())
                .map { it.toDomain() }
        } catch (e: Exception) {
            throw Exception("Could not load states for project $projectId", e)
        }
    }

    override fun createState(state: State) {
        try {
            statesDataSource.createState(StateDto.fromDomain(state))
        } catch (e: Exception) {
            throw Exception("Could not create state", e)
        }
    }

    override fun updateState(state: State) {
        try {
            statesDataSource.updateState(StateDto.fromDomain(state))
        } catch (e: Exception) {
            throw Exception("Could not update state", e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteState(stateId: Uuid) {
        try {
            statesDataSource.deleteState(stateId.toString())
        } catch (e: Exception) {
            throw Exception("Could not delete state $stateId", e)
        }
    }
}