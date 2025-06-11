package planmate.data.repository.datasource

import planmate.data.dto.StateDto

interface StatesDataSource {

    fun getAllStates(projectId: String): List<StateDto>

    fun createState(state: StateDto)

    fun updateState(state: StateDto)

    fun deleteState(stateId: String)
}