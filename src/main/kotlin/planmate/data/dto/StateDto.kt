package planmate.data.dto

import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class StateDto(
    val id: String,
    val name: String,
    val projectId: String,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toDomain(): State = State(
        id = Uuid.parse(id),
        name = name,
        projectId = Uuid.parse(projectId),
    )

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun fromDomain(state: State): StateDto =
            StateDto(
                id = state.id.toString(),
                name = state.name,
                projectId = state.projectId.toString()
            )
    }
}