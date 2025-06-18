package planmate.data.dto

import planmate.domain.models.Task
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val stateId: String,
    val projectId: String
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toDomain(): Task = Task(
        id = Uuid.parse(id),
        title = title,
        description = description,
        stateId = Uuid.parse(stateId),
        projectId = Uuid.parse(projectId)
    )

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun fromDomain(task: Task): TaskDto =
            TaskDto(
                id = task.id.toString(),
                title = task.title,
                description = task.description,
                stateId = task.stateId.toString(),
                projectId = task.projectId.toString()
            )
    }
}
