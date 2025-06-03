package planmate.domain.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class State @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val name: String,
    val projectId: Uuid
)