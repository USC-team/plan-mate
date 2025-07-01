package planmate.domain.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class User(
    val id: Uuid,
    val name: String,
    val role: Role
){
    enum class Role {
        ADMIN,
        MATE
    }
}