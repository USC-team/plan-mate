package planmate.domain.repository

import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface StatesRepository {

    @OptIn(ExperimentalUuidApi::class)
    fun getAllStates(projectId: Uuid): List<State>

    fun createState(state: State)

    fun updateState(state: State)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteState(stateId: Uuid)
}