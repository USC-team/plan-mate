package planmate.domain.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Task @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val description: String,
    val userId: Uuid,
    val stateId: Uuid,
    val projectId: Uuid
)
