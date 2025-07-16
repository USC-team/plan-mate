package planmate.data.dto

import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val stateId: String,
    val projectId: String
)
