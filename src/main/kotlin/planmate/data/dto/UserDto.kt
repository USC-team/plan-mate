package planmate.data.dto

import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
data class UserDto(
    val id: String,
    val name: String,
    val role: String
)