package planmate.domain.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class State(
    val id: Uuid,
    val name: String,
    val projectId: Uuid
)