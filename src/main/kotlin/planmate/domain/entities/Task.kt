package planmate.domain.entities

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val userId: String,
    val stateId: String,
    val projectId: String
)
