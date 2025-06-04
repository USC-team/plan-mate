package planmate.data.repository.datasource

import planmate.data.dto.StateDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface StatesDataSource {

    @OptIn(ExperimentalUuidApi::class)
    fun getAllStates(projectId: Uuid): List<StateDto>

    fun createState(state: StateDto)

    fun updateState(state: StateDto)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteState(stateId: Uuid)
}