package planmate.data.dto

import planmate.domain.models.Role
import planmate.domain.models.User

data class UserDto(
    val id: String,
    val name: String,
    val role: String
) {
    fun toDomain() = User(
        id = id,
        name = name,
        role = if (role == "ADMIN") Role.ADMIN
        else Role.MATE
    )

    companion object {
        fun fromDomain(user: User) =
            UserDto(
                id = user.id,
                name = user.name,
                role = user.role.toString()
            )
    }
}