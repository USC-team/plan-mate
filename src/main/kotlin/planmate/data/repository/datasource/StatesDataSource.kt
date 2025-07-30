package planmate.data.repository.datasource

import planmate.data.dto.StateDto

interface StatesDataSource {

    suspend fun getAllStates(projectId: String): List<StateDto>

    suspend fun createState(state: StateDto)

    suspend fun updateState(state: StateDto)

    suspend fun deleteState(stateId: String)
}