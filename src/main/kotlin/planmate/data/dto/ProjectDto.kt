package planmate.data.dto

import planmate.domain.models.Project
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ProjectDto(
    val id: String,
    val name: String,
    val userId: String,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toDomain() = Project(
        id = Uuid.parse(id),
        name = name,
        userId = Uuid.parse(userId)
    )

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun fromDomain(project: Project) =
            ProjectDto(
                id = project.id.toString(),
                name = project.name,
                userId = project.userId.toString()
            )
    }
}