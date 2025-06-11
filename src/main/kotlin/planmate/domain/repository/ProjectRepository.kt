package planmate.domain.repository

import planmate.domain.models.Project
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ProjectRepository {
    fun getAllProjects(): List<Project>

    fun createProject(project: Project)

    fun updateProject(project: Project)

    @OptIn(ExperimentalUuidApi::class)
    fun deleteProject(projectId: Uuid)
}