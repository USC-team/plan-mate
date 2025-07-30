package planmate.domain.repository

import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface StatesRepository {

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getAllStates(projectId: Uuid): List<State>

    suspend fun createState(state: State)

    suspend fun updateState(state: State)

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteState(stateId: Uuid)
}