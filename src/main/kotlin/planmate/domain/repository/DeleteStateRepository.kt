package planmate.domain.repository

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface DeleteStateRepository {
    @OptIn(ExperimentalUuidApi::class)
    fun deleteState( projectId: Uuid)
}