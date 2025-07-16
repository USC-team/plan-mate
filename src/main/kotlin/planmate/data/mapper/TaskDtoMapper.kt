package planmate.data.mapper

import planmate.data.dto.TaskDto
import planmate.domain.models.Task
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun TaskDto.toDomain(): Task = Task(
    id = Uuid.parse(id),
    title = title,
    description = description,
    stateId = Uuid.parse(stateId),
    projectId = Uuid.parse(projectId)
)

@OptIn(ExperimentalUuidApi::class)
fun Task.fromDomain(task: Task): TaskDto =
    TaskDto(
        id = task.id.toString(),
        title = task.title,
        description = task.description,
        stateId = task.stateId.toString(),
        projectId = task.projectId.toString()
    )