package planmate.data.mapper

import planmate.data.dto.StateDto
import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun StateDto.toDomain(): State = State(
    id = Uuid.parse(id),
    name = name,
    projectId = Uuid.parse(projectId),
)


@OptIn(ExperimentalUuidApi::class)
fun State.fromDomain(state: State): StateDto =
    StateDto(
        id = state.id.toString(),
        name = state.name,
        projectId = state.projectId.toString()
    )