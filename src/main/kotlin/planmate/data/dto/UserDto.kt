package planmate.data.dto

import planmate.domain.models.Role
import planmate.domain.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class UserDto(
    val id: String,
    val name: String,
    val role: String
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toDomain() = User(
        id = Uuid.parse(id),
        name = name,
        role = if (role == "ADMIN") Role.ADMIN
        else Role.MATE
    )

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun fromDomain(user: User) =
            UserDto(
                id = user.id.toString(),
                name = user.name,
                role = user.role.toString()
            )
    }
}