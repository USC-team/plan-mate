package planmate.domain.repository

import planmate.domain.models.Project
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ProjectRepository {
    suspend fun getAllProjects(): List<Project>

    suspend fun createProject(project: Project)

    suspend fun updateProject(project: Project)

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteProject(projectId: Uuid)
}