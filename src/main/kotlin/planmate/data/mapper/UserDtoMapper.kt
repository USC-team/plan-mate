package planmate.data.mapper

import planmate.data.dto.UserDto
import planmate.domain.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun UserDto.toDomain() = User(
    id = Uuid.parse(id),
    name = name,
    role = if (role == "ADMIN") User.Role.ADMIN
    else User.Role.MATE
)


@OptIn(ExperimentalUuidApi::class)
fun User.fromDomain(user: User) =
    UserDto(
        id = user.id.toString(),
        name = user.name,
        role = user.role.toString()
    )