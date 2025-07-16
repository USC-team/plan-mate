package planmate.data.mapper

import planmate.data.dto.ProjectDto
import planmate.domain.models.Project
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun ProjectDto.toDomain(): Project
    = Project(
        id = Uuid.parse(id),
        name = name,
        userId = Uuid.parse(userId)
    )


@OptIn(ExperimentalUuidApi::class)
fun Project.fromDomain(project: Project) =
    ProjectDto(
        id = project.id.toString(),
        name = project.name,
        userId = project.userId.toString()
    )
