package planmate.domain.repository

import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface GetAllStatesRepository {
    @OptIn(ExperimentalUuidApi::class)
    fun getAllStates(projectId: Uuid): List<State>
}